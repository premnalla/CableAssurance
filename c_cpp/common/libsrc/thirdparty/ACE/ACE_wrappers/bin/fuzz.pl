eval '(exit $?0)' && eval 'exec perl -S $0 ${1+"$@"}'
    & eval 'exec perl -S $0 $argv:q'
    if 0;

# fuzz.pl,v 1.101 2006/02/15 20:14:27 elliott_c Exp
#   Fuzz is a script whose purpose is to check through ACE/TAO files for
#   easy to spot (by a perl script, at least) problems.

use File::Find;
use File::Basename;
use Getopt::Std;

###### TODO
#
# Add tests for these:
#
# - no relative path to tao_idl in the .dsp files
# - Linking to wrong type of library in dsp's
# - not setting up the release configs correctly in dsp files
# - Guards in .h files
# - no global functions
# - other commit_check checks, tabs, trailing spaces.
# - _narrow() should always have ACE_ENV_ARG_PARAMETER
# - Using ACE_TRY_NEW_ENV (Nanbor suggests using ACE_DECLARE_NEW_CORBA_ENV)
#
# And others in ACE_Guidelines and Design Rules
#
# Also add a -g flag to ignore tao_idl generated files
#
###### END TODO

# Lists of all the files
@files_cpp = ();
@files_inl = ();
@files_h = ();
@files_html = ();
@files_dsp = ();
@files_dsw = ();
@files_gnu = ();
@files_idl = ();
@files_pl = ();
@files_changelog = ();
@files_makefile = ();
@files_mpc = ();
@files_bor = ();
@files_noncvs = ();

# To keep track of errors and warnings
$errors = 0;
$warnings = 0;

##############################################################################

# Find_Modified_Files will use 'cvs -nq' to get a list of locally modified
# files to look through
sub find_mod_files ()
{
    unless (open (CVS, "cvs -nq up |")) {
        print STDERR "Error: Could not run cvs\n";
        exit (1);
    }

    while (<CVS>) {
        if (/^[M|A] (.*)/) {
            store_file ($1);
        }
    }
    close (CVS);
}



# Find_Files will search for files with certain extensions in the
# directory tree
sub find_files ()
{
    # wanted is only used for the File::Find
    sub wanted
    {
        store_file ($File::Find::name);
    }

    find (\&wanted, '.');
}

#
sub store_file ($)
{
    my $name = shift;
    if ($name =~ /\.(c|cc|cpp|cxx)$/i) {
        push @files_cpp, ($name);
    }
    elsif ($name =~ /\.(inl|i)$/i) {
        push @files_inl, ($name);
    }
    elsif ($name =~ /\.(h|hh|hpp|hxx)$/i) {
        push @files_h, ($name);
    }
    elsif ($name =~ /\.(htm|html)$/i) {
        push @files_html, ($name);
    }
    elsif ($name =~ /\.(bor)$/i) {
        push @files_bor, ($name);
    }
    elsif ($name =~ /\.(GNU)$/i) {
        push @files_gnu, ($name);
    }
    elsif ($name =~ /\.(dsp|vcp)$/i) {
        push @files_dsp, ($name);
    }
    elsif ($name =~ /\.(dsw|vcp)$/i) {
        push @files_dsw, ($name);
    }
    elsif ($name =~ /\.(pidl|idl)$/i) {
        push @files_idl, ($name);
    }
    elsif ($name =~ /\.pl$/i) {
        push @files_pl, ($name);
    }
    elsif ($name =~ /ChangeLog/i && -f $name) {
        push @files_changelog, ($name);
    }
    elsif ($name =~ /\/GNUmakefile.*.[^~]$/) {
        push @files_makefile, ($name);
    }
    elsif ($name =~ /\.(mpc|mwc|mpb|mpt)/i) {
        push @files_mpc, ($name);
    }
    elsif ($name =~ /\.(icc|ncb|opt|zip)$/i) {
        push @files_noncvs, ($name);
    }
}

##############################################################################
## Just messages

sub print_error ($)
{
    my $msg = shift;
    print "Error: $msg\n";
    ++$errors;
}


sub print_warning ($)
{
    my $msg = shift;
    print "Warning: $msg\n";
    ++$warnings;
}


##############################################################################
## Tests

# The point of this test is to check for the existence of ACE_INLINE
# or ASYS_INLINE in a .cpp file.  This is most commonly caused by
# copy/pasted code from a .inl/.i file
sub check_for_inline_in_cpp ()
{
    print "Running ACE_INLINE/ASYS_INLINE check\n";
    foreach $file (@files_cpp) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/^ACE_INLINE/) {
                    print_error ("$file:$.: ACE_INLINE found");
                }
                if (/^ASYS_INLINE/) {
                    print_error ("$file:$.: ASYS_INLINE found");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks to make sure files have the $Id string in them.
# Commit_check should find these when checking in files, but this can
# be used locally or to check for files
sub check_for_id_string ()
{
    print "Running \$Id\$ string check\n";
    foreach $file (@files_cpp, @files_inl, @files_h, @files_mpc, @files_bor, @files_gnu,
                   @files_html, @files_idl, @files_pl, @makefile_files) {
        my $found = 0;
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/\$Id\:/ or /\$Id\$/) {
                    $found = 1;
                }
                if (/\$id\$/) {
                    print_error ("$file:$.: Incorrect \$id\$ found (correct casing)");
                }
            }
            close (FILE);
            if ($found == 0) {
                print_error ("$file:1: No \$Id\$ string found.");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# check for _MSC_VER >= 1200
sub check_for_msc_ver_string ()
{
    print "Running _MSC_VER check\n";
    foreach $file (@files_cpp, @files_inl, @files_h) {
        my $found = 0;
        if (open (FILE, $file)) {
            my $disable = 0;
            my $mscline = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_msc_ver/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_msc_ver/) {
                    $disable = 0;
                }
                if ($disable == 0 and /\_MSC_VER \>= 1200/) {
                    $found = 1;
                    $mscline = $.;
                }
            }
            close (FILE);
            if ($found == 1) {
               print_error ("$file:$mscline: Incorrect _MSC_VER >= 1200 found");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for the newline at the end of a file
sub check_for_newline ()
{
    print "Running newline check\n";
    foreach $file (@files_cpp, @files_inl, @files_h,
                   @files_html, @files_idl, @files_pl) {
        if (open (FILE, $file)) {
            my $line;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                $line = $_
            }
            close (FILE);
            if ($line !~ /\n$/) {
                print_error ("$file:$.: No ending newline found in $file");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}


# This test checks for files that are not allowed to be in cvs
sub check_for_noncvs_files ()
{
    print "Running non cvs files check\n";
    foreach $file (@files_noncvs, @files_dsp, @files_dsw, @files_makefile) {
        print_error ("File $file should not be in cvs!");
    }
}


# This test checks for the use of "inline" instead of ACE_INLINE
sub check_for_inline ()
{
    print "Running inline check\n";
    foreach $file (@files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                ++$line;
                if (/FUZZ\: disable check_for_inline/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_inline/) {
                    $disable = 0;
                }
                if ($disable == 0 and m/^\s*inline/) {
                    print_error ("$file:$.: 'inline' keyword found");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}


# This test checks for the inclusion of math.h.  math.h should be avoided
# since on some platforms, "exceptions" is defined as a struct, which will
# cause problems with exception handling
sub check_for_math_include ()
{
    print "Running math.h test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_math_include/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_math_include/) {
                    $disable = 0;
                }
                if ($disable == 0
                    and /^\s*#\s*include\s*(\/\*\*\/){0,1}\s*\<math\.h\>/) {
                    print_error ("$file:$.: <math.h> included");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for the inclusion of streams.h.
# // FUZZ: disable check_for_streams_include
sub check_for_streams_include ()
{
    print "Running ace/streams.h test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_streams_include/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_streams_include/) {
                    $disable = 0;
                }
                if ($disable == 0
                    and /^\s*#\s*include\s*\"ace\/streams\.h\"/) {
                    print_error ("$file:$.: expensive ace/streams.h included; consider ace/iosfwd.h");
                    print " ace/streams.h is very expensive in both ";
                    print "compile-time and footprint. \n";
                    print " Please consider including ace/iosfwd.h instead.\n\n";
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for the inclusion of OS.h.
sub check_for_OS_h_include ()
{
    print "Running ace/OS.h test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_OS_h_include/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_OS_h_include/) {
                    $disable = 0;
                }
                if ($disable == 0
                    and /^\s*#\s*include\s*\"ace\/OS\.h\"/) {
                    print_error ("$file:$.: expensive ace/OS.h included; consider an OS_NS_*.h file");
                    print " OS.h is very expensive in both ";
                    print "compile-time and footprint. \n";
                    print " Please consider including one of the ";
                    print "OS_NS_*.h files instead.\n\n";
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for the inclusion of Synch*.h.
sub check_for_synch_include ()
{
    print "Running ace/Synch*.h test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_synch_include/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_synch_include/) {
                    $disable = 0;
                }
                if ($disable == 0
                    and (/^\s*#\s*include\s*\"(ace\/Synch\.h)\"/
                         or /^\s*#\s*include\s*\"(ace\/Synch_T\.h)\"/)) {
                    my $synch = $1;
                    print_error ("$file:$.: expensive $synch included;  consider individual synch file");
                    print " $synch is very expensive in both ";
                    print "compile-time and footprint. \n";
                    print " Please consider including one of the ";
                    print "individual synch files instead.\n\n";
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# For general readability, lines should not contain more than 80 characters
sub check_for_line_length ()
{
    print "Running line length test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {

                # Make sure to ignore ACE_RCSID lines, since they
                # are difficult to get under 80 chars.
                if (/.{80,}/ and !/^ACE_RCSID/) {
                    print_error ("$file:$.: line longer than 80 chars");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}


# For preprocessor directives, only the old C style comments (/* */)
# should be used, not the newer // style.
sub check_for_preprocessor_comments ()
{
    print "Running preprocessor comment test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/^\#.*\/\//) {
                    print_error ("$file:$.: C++ comment in directive");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# We should not have empty inline files in the repo
sub check_for_empty_inline_files ()
{
    print "Running empty inline files test\n";
    foreach $file (@files_inl) {
        my $found_non_empty_line = 0;
        my $idl_generated = 0;
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
              next if /^[:blank:]*$/; # skip empty lines
              next if /^[:blank:]*\/\//; # skip C++ comments
              next if /^[:blank:]*\/\*/; # skip C++ comments
              $found_non_empty_line = 1;
              last;
            }
            close (FILE);
            if ($found_non_empty_line == 0 and $idl_generated == 0) {
             print_error ("$file:1: empty inline file should not be in the repository");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}


# This test checks for the use of the Win32 Unicode string defines
# or outdated ASYS_* macros
# We should only be using the ACE_TCHAR, ACE_TEXT macros instead.
sub check_for_tchar
{
    print "Running TCHAR test\n";
    foreach $file (@files_h, @files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_tchar/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_tchar/) {
                    $disable = 0;
                }
                if ($disable == 0) {
                    if (/LPTSTR/) {
                        print_error ("$file:$.: LPTSTR found");
                    }

                    if (/LPCTSTR/) {
                        print_error ("$file:$.: LPCTSTR found");
                    }

                    if (/ASYS_TCHAR/) {
                        print_error ("$file:$.: ASYS_TCHAR found");
                    }
                    elsif (/TCHAR/ and !/ACE_TCHAR/) {
                        ### Do a double check, since some macros do have TCHAR
                        ### (like DEFAULTCHARS)
                        if (/^TCHAR[^\w_]/ or /[^\w_]TCHAR[^\w_]/) {
                            print_error ("$file:$.: TCHAR found");
                        }
                    }

                    if (/ASYS_TEXT/) {
                        print_error ("$file:$.: ASYS_TEXT found");
                    }
                    elsif (/TEXT/ and !/ACE_TEXT/) {
                        ### Do a double check, since there are several macros
                        ### that end with TEXT
                        if (/^TEXT\s*\(/ or /[^\w_]TEXT\s*\(/) {
                            print_error ("$file:$.: TEXT found");
                        }
                    }
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This checks to see if Makefiles define a DEPENDENCY_FILE, and if they do
# whether or not it's in the cvs repo.
sub check_for_dependency_file ()
{
    print "Running DEPENDENCY_FILE test\n";
    foreach $file (@files_makefile) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/^DEPENDENCY_FILE\s* =\s*(.*)/) {
                    my $depend = $1;
                    my $path = $file;
                    $path =~ s/\/GNUmakefile.*/\//;
                    $depend = $path . $depend;
                    unless (open (DFILE, $depend)) {
                        print_error ("DEPENDENCY_FILE \"$depend\" not found");
                        print " Either add \"$depend\" to cvs ";
                        print "or remove DEPENDENCY_FILE variable\n";
                        print " from $file\n\n";
                    }
                    close (DFILE);
                }
            }
            close (FILE);
        }
        else {
            print_error ("cannot open $file");
        }
    }
}

# This checks to see if GNUmakefiles define a MAKEFILE, and if it matches the
# name of the GNUmakefile
sub check_for_makefile_variable ()
{
    print "Running MAKEFILE variable test\n";
    foreach $file (@files_makefile) {
        if (!(substr($file,-4) eq ".bor")
            and !(substr($file,-3) eq ".am")
            and !(substr($file,-4) eq ".vac")
            and !(substr($file,-4) eq ".alt")) {
            if (open (FILE, $file)) {
                print "Looking at file $file\n" if $opt_d;
                my $makevarfound = 0;
                my $filename = basename($file,"");
                while (<FILE>) {
                    if (/^MAKEFILE\s*=\s*(.*)/) {
                        $makevarfound = 1;
                        $makevar = $1;
                        if (!($makevar eq $filename)) {
                            print_error ("$file:$.: MAKEFILE variable $makevar != $filename");
                            print " Change MAKEFILE = $filename in $file.\n\n";
                        }
                    }
                }
                if ($makevarfound == 0 and !($filename eq "GNUmakefile")) {
                    print_error ("$file:$.: MAKEFILE variable missing in $file");
                    print " Add MAKEFILE = $filename to the top of $file.\n\n";
                }
                close (FILE);
            }
            else {
                print_error ("cannot open $file");
            }
        }
    }
}


# This checks to make sure files include ace/post.h if ace/pre.h is included
# and vice versa.
sub check_for_pre_and_post ()
{
    print "Running pre.h/post.h test\n";
    foreach $file (@files_h) {
        my $pre = 0;
        my $post = 0;
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_pre_and_post/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_pre_and_post/) {
                    $disable = 0;
                }
                if ($disable == 0) {
                    if (/^\s*#\s*include\s*\"ace\/pre\.h\"/) {
                        print_error ("$file:$.: pre.h  missing \"/**/\"");
                        ++$pre;
                    }
                    if (/^\s*#\s*include\s*\s*\"ace\/post\.h\"/) {
                        print_error ("$file:$.: post.h missing \"/**/\"");
                        ++$post;
                    }
                    if (/^\s*#\s*include\s*\/\*\*\/\s*\"ace\/pre\.h\"/) {
                        ++$pre;
                    }
                    if (/^\s*#\s*include\s*\/\*\*\/\s*\"ace\/post\.h\"/) {
                        ++$post;
                    }
                }
            }
            close (FILE);

            if ($disable == 0 && $pre != $post) {
                print_error ("$file:1: pre.h/post.h mismatch");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test verifies that the same number of "#pragma warning(push)" and
# "#pragma warning(pop)" pragmas are used in a given header.
sub check_for_push_and_pop ()
{
    print "Running #pragma (push)/(pop) test\n";
    foreach $file (@files_h) {
        my $push_count = 0;
        my $pop_count = 0;
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_push_and_pop/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_push_and_pop/) {
                    $disable = 0;
                }
                if ($disable == 0) {
                    if (/^\s*#\s*pragma\s*warning\s*\(\s*push[,1-4]*\s*\)/) {
                        ++$push_count;
                    }
                    if (/^\s*#\s*pragma\s*warning\s*\(\s*pop\s*\)/) {
                        ++$pop_count;
                    }
                }
            }
            close (FILE);

            if ($disable == 0 && $push_count != $pop_count) {
                print_error ("$file: #pragma warning(push)/(pop) mismatch");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test verifies that the same number of
# "ACE_VERSIONED_NAMESPACE_BEGIN_DECL" and
# "ACE_END_VERSIONED_NAMESPACE_DECL" macros are used in a given
# source file.
sub check_for_versioned_namespace_begin_end ()
{
  print "Running versioned namespace begin/end test\n";
  foreach $file (@files_cpp, @files_inl, @files_h) {
    my $begin_count = 0;
    my $end_count = 0;
    if (open (FILE, $file)) {
      print "Looking at file $file\n" if $opt_d;
      while (<FILE>) {
        if (/^\s*\w+_BEGIN_VERSIONED_NAMESPACE_DECL/) {
          ++$begin_count;
        }
        if (/^\s*\w+_END_VERSIONED_NAMESPACE_DECL/) {
          ++$end_count;
        }
        if ($begin_count > $end_count and /^\s*#\s*include\s*/) {
          print_error ("$file:$.: #include directive within Versioned namespace block");
        }
      }

      close (FILE);

      if ($begin_count != $end_count) {
        print_error ("$file: Versioned namespace begin($begin_count)/end($end_count) mismatch");
      }
    }
    else {
      print STDERR "Error: Could not open $file\n";
    }
  }
}


# Check doxygen @file comments
sub check_for_mismatched_filename ()
{
    print "Running doxygen \@file test\n";
    foreach $file (@files_h, @files_cpp, @files_inl, @files_idl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (m/\@file\s*([^\s]+)/){
                    # $file includes complete path, $1 is the name after
                    # @file. We must strip the complete path from $file.
                    # we do that using the basename function from
                    # File::BaseName
                    $filename = basename($file,"");
                    if (!($filename eq $1)){
                        print_error ("$file:$.: \@file mismatch in $file, found $1");
                    }
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# check for bad run_test
sub check_for_bad_run_test ()
{
    print "Running run_test.pl test\n";
    foreach $file (@files_pl) {
        if (open (FILE, $file)) {
            my $is_run_test = 0;
            my $sub = 0;

            print "Looking at file $file\n" if $opt_d;

            while (<FILE>) {

                if (m/PerlACE/ || m/ACEutils/) {
                    $is_run_test = 1;
                }

                if ($is_run_test == 1) {
                    if (m/ACEutils/) {
                        print_error ("$file:$.: ACEutils.pm still in use");
                    }

                    if (m/unshift \@INC/) {
                        print_error ("$file:$.: unshifting \@INC; use \"use lib\"");
                    }

                    if (m/\$EXEPREFIX/) {
                        print_error ("$file:$.: using \$EXEPREFIX");
                    }

                    if (m/\$EXE_EXT/) {
                        print_error ("$file:$.: using \$EXE_EXT");
                    }

                    if (m/\$DIR_SEPARATOR/) {
                        print_error ("$file:$.: using \$DIR_SEPARATOR");
                    }
                    if (m/ACE\:\:/ && !m/PerlACE\:\:/) {
                        print_error ("$file:$.: using ACE::*");
                    }

                    if (m/Process\:\:/ && !m/PerlACE\:\:Process\:\:/) {
                        print_error ("$file:$.: using Process::*");
                    }

                    if (m/Process\:\:Create/) {
                        print_error ("$file:$.: using Process::Create");
                    }

                    if ((m/\.ior/ || m/\.conf/) && !m/LocalFile/) {
                        print_error ("$file:$.: Not using PerlACE::LocalFile");
                    }

                    if (m/^  [^ ]/) {
                        print_warning ("$file:$.: using two-space indentation");
                    }

                    if (m/^\s*\t/) {
                        print_error ("$file:$.: Indenting using tabs");
                    }

                    if (m/^\s*\{/ && $sub != 1) {
                        print_warning ("$file:$.: Using Curly Brace alone");
                    }

                    if (m/timedout/i && !m/\#/) {
                        print_error ("$file:$.: timedout message found");
                    }

                    if (m/^\s*sub/) {
                        $sub = 1;
                    }
                    else {
                        $sub = 0;
                    }
                }
            }

            close (FILE);

            if ($is_run_test) {
                my @output = `perl -wc $file 2>&1`;

                foreach $output (@output) {
                    chomp $output;
                    if ($output =~ m/error/i) {
                        print_error ($output);
                    }
                    elsif ($output !~ m/syntax OK/) {
                        print_warning ($output);
                    }
                }
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}


# Check for links to ~schmidt/ACE_wrappers/, which should not be in the
# documentation
sub check_for_absolute_ace_wrappers()
{
    print "Running absolute ACE_wrappers test\n";
    foreach $file (@files_html) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (m/\~schmidt\/ACE_wrappers\//) {
                    chomp;
                    print_error ("$file:$.: ~schmidt/ACE_wrappers found");
                    print_error ($_) if (defined $opt_v);
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# Make sure ACE_[OS_]TRACE matches the function/method
sub check_for_bad_ace_trace()
{
    print "Running TRACE test\n";
    foreach $file (@files_inl, @files_cpp) {
        if (open (FILE, $file)) {
            my $class;
            my $function;

            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {

                # look for methods or functions
                if (m/(^[^\s][^\(]*)\:\:([^\:^\(]*[^\s^\(])\s*/) {
                    $class = $1;
                    $function = $2;
                }
                elsif (m/^([^\s^\(^\#]*) \(/i) {
                    $class = "";
                    $function = $1;
                }
                elsif (m/^(operator.*) \(/i) {
                    $class = "";
                    $function = $1;
                }

                # Look for TRACE statements
                if (m/ACE_OS_TRACE\s*\(\s*\"(.*)\"/
                    || m/ACE_TRACE\s*\(\s*\"(.*)\"/) {
                    my $trace = $1;

                    # reduce the classname
                    if ($class =~ m/([^\s][^\<^\s]*)\s*\</) {
                        $class = $1;
                    }

                    if ($class =~ m/([^\s^\&^\*]*)\s*$/) {
                        $class = $1;
                    }

                    if ($trace !~ m/\Q$function\E/
                        || ($trace =~ m/\:\:/ && !($trace =~ m/\Q$class\E/ && $trace =~ m/\Q$function\E/))) {
                        print_error ("$file:$.: Mismatched TRACE");
                        print_error ("$file:$.:   I see \"$trace\" but I think I'm in \""
                                     . $class . "::" . $function . "\"") if (defined $opt_v);
                    }
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}



# This test checks missing ACE_ENV_ARG_PARAMETER when using
# resolve_initial_references
sub check_for_missing_rir_env ()
{
    print "Running resolve_initial_references() check\n";
    foreach $file (@files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            my $native_try = 0;
            my $in_rir = 0;
            my $found_env = 0;

            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_missing_rir_env/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_missing_rir_env/) {
                    $disable = 0;
                }
                if ($disable == 0) {
                    next if m/^\s*\/\//;

                    if (m/^\s*try/) {
                        $disable = 1;
                        next;
                    }

                    if (m/[^\:]resolve_initial_references\s*\(/) {
                        $found_env = 0;
                        $in_rir = 1;
                    }

                    if (m/ACE_ENV_ARG_PARAMETER/) {
                        $found_env = 1;
                    }

                    if ($in_rir == 1 && m/\;\s*$/) {
                        $in_rir = 0;
                        if ($found_env != 1) {
                            print_error ("$file:$.: Missing ACE_ENV_ARG_PARAMETER in"
                                         . " resolve_initial_references");
                        }
                        $found_env = 0;
                    }

                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for usage of ACE_CHECK/ACE_TRY_CHECK
sub check_for_ace_check ()
{
    print "Running ACE_CHECK check\n";
    foreach $file (@files_cpp, @files_inl) {
        if (open (FILE, $file)) {
            my $disable = 0;
            my $in_func = 0;
            my $in_return = 0;
            my $in_ace_try = 0;
            my $found_env = 0;

            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_ace_check/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_ace_check/) {
                    $disable = 0;
                }

                if (/FUZZ\: ignore check_for_ace_check/) {
                    next;
                }

                next if m/^\s*\/\//;
                next if m/^\s*$/;

                if (m/ACE_TRY\s*$/ || m/ACE_TRY_EX/ || m/ACE_TRY\s*{/) {
                  $in_ace_try = 1;
                }
                if (m/ACE_CATCH/) {
                  $in_ace_try = 0;
                }

                if ($disable == 0) {
                    if (m/\s*ACE_ENV_(SINGLE_)?ARG_PARAMETER[,\)]/) {
                        $env_line = $.;
                        if ($found_env) {
                            print_error ("$file:$env_line: Missing ACE_CHECK/ACE_TRY_CHECK");
                        }
                        $found_env = 1;
                        $in_func = 1;
                    }

                    if (m/^\s*return/) {
                      $in_return = 1;
                    }
                    if ($in_return && m/;/) {
                      $in_return = 0;
                      $found_env = 0;
                    }

                    # ignore quoted ACE_ENV_ARG_PARAMETERS's
                    if (m/^[^\"]*\"[^\"]*ACE_ENV_(SINGLE_)?ARG_PARAMETER[^\"]*\"[^\"]*$/) {
                        $found_env = 0;
                    }

                    if (m/ACE_ENV_(SINGLE_)?ARG_PARAMETER.*ACE_ENV_(SINGLE_)?ARG_PARAMETER/) {
                        print_error ("$file:$.: Multiple ACE_ENV_ARG_PARAMETER");
                    }

                    if (m/ACE_THROW/ && $in_ace_try) {
                        print_error ("$file:$.: ACE_THROW/ACE_THROW_RETURN used inside of an ACE_TRY");
                    }

                    if ($in_func && m/\)/) {
                      $in_func = 0;
                    }
                    elsif (!$in_func && $found_env) {
                        if (m/ACE_CHECK/ && $in_ace_try) {
                            print_error ("$file:$.: ACE_CHECK/ACE_CHECK_RETURN used inside of an ACE_TRY");
                        }
                        elsif (!m/_CHECK/ && !m/^\}/ && !$in_return) {
                            print_error ("$file:$env_line: Missing ACE_CHECK/ACE_TRY_CHECK");
                        }
                        $found_env = 0;
                    }
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for broken ChangeLog entries.
sub check_for_changelog_errors ()
{
    print "Running ChangeLog check\n";
    foreach $file (@files_changelog) {
        if (open (FILE, $file)) {
            my $found_backslash = 0;
            my $found_cvs_conflict = 0;

            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {

                next if m/^\s*\/\//;
                next if m/^\s*$/;

                # Check for backslashes in paths.
                if (m/\*.*\\[^ ]*:/) {
                    print_error ("$file:$.: Backslashes in file path");
                }

                # Check for CVS conflict tags
                if (m/^<<<<</ || m/^=====/ || m/^>>>>>/) {
                    print_error ("$file:$.: CVS conflict markers");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for ptr_arith_t usage in source code.  ptr_arith_t
# is non-portable.  Use ptrdiff_t instead.
sub check_for_ptr_arith_t ()
{
    print "Running ptr_arith_t check\n";
    foreach $file (@files_cpp, @files_inl, @files_h) {
        if (open (FILE, $file)) {

            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {

                next if m/^\s*\/\//;  # Ignore C++ comments.
                next if m/^\s*$/;     # Skip lines only containing
                                      # whitespace.

                # Check for ptr_arith_t usage.  This test should
                # ignore typedefs, and should only catch variable
                # declarations and parameter types.
                if (m/ptr_arith_t / || m/ptr_arith_t,/) {
                    print_error ("$file:$.: ptr_arith_t; use ptrdiff_t instead.");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test checks for the #include <ace/...>
# This check is suggested by Don Hinton to force user to use
# " " instead of <> to avoid confict with Doxygen.
sub check_for_include ()
{
    print "Running the include check\n";
    foreach $file (@files_h, @files_cpp, @files_inl, @files_idl) {
        my $bad_occurance = 0;
        if (open (FILE, $file)) {
            my $disable = 0;
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {
                if (/FUZZ\: disable check_for_include/) {
                    $disable = 1;
                }
                if (/FUZZ\: enable check_for_include/) {
                    $disable = 0;
                }
                if ($disable == 0) {
                    if (/^\s*#\s*include\s*<[(ace)|(TAO)|(CIAO)]\/.*>/) {
                        print_error ("$file:$.: include <ace\/..> used") if ($opt_v);
                        ++$bad_occurance;
                    }
                    if (/^\s*#\s*include\s*<tao\/.*>/) {
                        print_error ("$file:$.: include <tao\/..> used") if ($opt_v);
                        ++$bad_occurance;
                    }
                    if (/^\s*#\s*include\s*<ciao\/.*>/) {
                        print_error ("$file:$.: include <ciao\/..> used") if ($opt_v);
                        ++$bad_occurance;
                    }
                }
            }
            close (FILE);

            if ($disable == 0 && $bad_occurance > 0 ) {
                print_error ("$file:1: found $bad_occurance usage(s) of #include <> of ace\/tao\/ciao.");
            }
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test verifies that all equality, relational and logical
# operators return bool, as is the norm for modern C++.
#
# NOTE:  This test isn't fool proof yet.
sub check_for_non_bool_operators ()
{
    print "Running non-bool equality, relational and logical operator check\n";
    foreach $file (@files_h, @files_inl, @files_cpp) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            my $found_bool = 0;
            my $found_return_type = 0;
            while (<FILE>) {

                if ($found_bool == 0
                    && (/[^\w]bool\s*$/
                        || /^bool\s*$/
                        || /\sbool\s+\w/
                        || /^bool\s+\w/
                        || /[^\w]return\s*$/))
                  {
                    $found_bool = 1;
                    $found_return_type = 0;
                    next;
                  }

                if ($found_bool == 0 && $found_return_type == 0
                    && /^(?:\w+|\s+\w+)\s*$/
                    && !/[^\w]return\s*$/)
                  {
                    $found_return_type = 1;
                    $found_bool = 0;
                    next;
                  }

                if ($found_bool == 0
                    && /(?<![^\w]bool)(\s+|\w+::|>\s*::)operator\s*(?:!|<|<=|>|>=|==|!=|&&|\|\|)\s*\(/
                    && !/\(.*operator\s*(?:!|<|<=|>|>=|==|!=|&&|\|\|)\s*\(/
                    && !/^\s*return\s+/) {
                    print_error ("$file:$.: non-bool return type for operator");
                }

                $found_return_type = 0;
                $found_bool = 0;
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

# This test verifies that all filenames are short enough

sub check_for_long_file_names ()
{
    my $max_filename = 50;
    my $max_mpc_projectname = $max_filename - 12; ## GNUmakefile.[project_name]
    print "Running file names check\n";

    foreach $file (@files_cpp, @files_inl, @files_h, @files_html,
                   @files_dsp, @files_dsw, @files_gnu, @files_idl,
                   @files_pl, @files_changelog, @files_makefile,
                   @files_bor, @files_mpc) {
        if ( length( basename($file) ) >= $max_filename )
        {
            print_error ("File name $file meets or exceeds $max_filename chars.");
        }
    }
    foreach $file (grep(/\.mpc$/, @files_mpc)) {
      if (open(FH, $file)) {
        my($blen) = length(basename($file)) - 4; ## .mpc
        while(<FH>) {
          if (/project\s*(:.*)\s*{/) {
            if ($blen >= $max_mpc_projectname) {
              print_warning ("File name $file meets or exceeds $max_mpc_projectname chars.");
            }
          }
          elsif (/project\s*\(([^\)]+)\)/) {
            my($name) = $1;
            if ($name =~ /\*/) {
              my($length) = length($name) + (($name =~ tr/*//) * $blen);
              if ($length >= $max_mpc_projectname) {
                print_warning ("Project name ($name) from $file will meet or exceed $max_mpc_projectname chars when expanded by MPC.");
              }
            }
            else {
              if (length($name) >= $max_mpc_projectname) {
                print_warning ("Project name ($name) from $file meets or exceeds $max_mpc_projectname chars.");
              }
            }
          }
        }
        close(FH);
      }
    }
}

sub check_for_refcountservantbase ()
{
    print "Running PortableServer::RefCountServantBase derivation check\n";

    foreach $file (@files_h) {
        if (open (FILE, $file)) {
            print "Looking at file $file\n" if $opt_d;
            while (<FILE>) {

                if (/PortableServer::RefCountServantBase/) {
                  print_error ("$file:$.: reference to deprecated PortableServer::RefCountServantBase");
                }
            }
            close (FILE);
        }
        else {
            print STDERR "Error: Could not open $file\n";
        }
    }
}

##############################################################################

use vars qw/$opt_c $opt_d $opt_h $opt_l $opt_t $opt_m $opt_v/;

if (!getopts ('cdhl:t:mv') || $opt_h) {
    print "fuzz.pl [-cdhm] [-l level] [-t test_name][file1, file2, ...]\n";
    print "\n";
    print "    -c             only look at the files passed in\n";
    print "    -d             turn on debugging\n";
    print "    -h             display this help\n";
    print "    -l level       set detection level (default = 5)\n";
    print "    -t test_name   specify any single test to run. This will disable the run level setting\n";
    print "    -m             only check locally modified files (uses cvs)\n";
    print "    -v             verbose mode\n";
    print "======================================================\n";
    print "list of the tests that could be run:\n";
    print "\t   check_for_noncvs_files
           check_for_synch_include
           check_for_OS_h_include
           check_for_streams_include
           check_for_dependency_file
           check_for_makefile_variable
           check_for_inline_in_cpp
           check_for_id_string
           check_for_newline
           check_for_inline
           check_for_math_include
           check_for_line_length
           check_for_preprocessor_comments
           check_for_tchar
           check_for_pre_and_post
           check_for_push_and_pop
           check_for_versioned_namespace_begin_end
           check_for_mismatched_filename
           check_for_bad_run_test
           check_for_absolute_ace_wrappers
           check_for_bad_ace_trace
           check_for_missing_rir_env
           check_for_ace_check
           check_for_changelog_errors
           check_for_ptr_arith_t
           check_for_include
           check_for_non_bool_operators
           check_for_long_file_names
           check_for_refcountservantbase\n";
    exit (1);
}

if (!$opt_l) {
    $opt_l = 5;
}

if ($opt_c) {
    foreach $file (@ARGV) {
        store_file ($file);
    }
}
elsif ($opt_m) {
    find_mod_files ();
}
else {
    find_files ();
}

if ($opt_t) {
    &$opt_t();
    exit (1);
}

print "--------------------Configuration: Fuzz - Level ",$opt_l,
      "--------------------\n";

check_for_refcountservantbase () if ($opt_l > 1 );
check_for_msc_ver_string () if ($opt_l >= 3);
check_for_empty_inline_files () if ($opt_l >= 1);
check_for_noncvs_files () if ($opt_l >= 1);
check_for_streams_include () if ($opt_l >= 6);
check_for_dependency_file () if ($opt_l >= 1);
check_for_makefile_variable () if ($opt_l >= 1);
check_for_inline_in_cpp () if ($opt_l >= 2);
check_for_id_string () if ($opt_l >= 1);
check_for_newline () if ($opt_l >= 1);
check_for_inline () if ($opt_l >= 2);
check_for_math_include () if ($opt_l >= 3);
check_for_synch_include () if ($opt_l >= 6);
check_for_OS_h_include () if ($opt_l >= 6);
check_for_line_length () if ($opt_l >= 8);
check_for_preprocessor_comments () if ($opt_l >= 7);
check_for_tchar () if ($opt_l >= 4);
check_for_pre_and_post () if ($opt_l >= 4);
check_for_push_and_pop () if ($opt_l >= 4);
check_for_versioned_namespace_begin_end () if ($opt_l >= 4);
check_for_mismatched_filename () if ($opt_l >= 2);
check_for_bad_run_test () if ($opt_l >= 6);
check_for_absolute_ace_wrappers () if ($opt_l >= 3);
check_for_bad_ace_trace () if ($opt_l >= 4);
check_for_missing_rir_env () if ($opt_l >= 6);
check_for_ace_check () if ($opt_l >= 6);
check_for_changelog_errors () if ($opt_l >= 4);
check_for_ptr_arith_t () if ($opt_l >= 4);
check_for_include () if ($opt_l >= 5);
check_for_non_bool_operators () if ($opt_l > 2);
check_for_long_file_names () if ($opt_l > 1 );


print "\nFuzz.pl - $errors error(s), $warnings warning(s)\n";

exit (1) if $errors > 0;
