eval '(exit $?0)' && eval 'exec perl -w -S $0 ${1+"$@"}'
    & eval 'exec perl -w -S $0 $argv:q'
    if 0;

# ******************************************************************
#      Author: Chad Elliott
# Create Date: 3/09/2004
#         prj_install.pl,v 1.5 2005/08/22 12:12:14 elliott_c Exp
# ******************************************************************

# ******************************************************************
# Pragma Section
# ******************************************************************

use strict;
use FileHandle;
use File::Copy;
use File::Basename;

# ******************************************************************
# Data Section
# ******************************************************************

my($insext)  = 'ins';
my($version) = 'prj_install.pl,v 1.5 2005/08/22 12:12:14 elliott_c Exp';
$version =~ s/.*\s+(\d+[\.\d]+)\s+.*/$1/;

my(%defaults) = ('header_files'   => 1,
                 'idl_files'      => 1,
                 'inline_files'   => 1,
                 'pidl_files'     => 1,
                 'template_files' => 1,
                );

my(%special)  = ('exe_output' => 1,
                 'lib_output' => 1,
                );

my(%actual)    = ();
my(%base)      = ();
my(%override)  = ();
my($keepgoing) = 0;

# ******************************************************************
# Subroutine Section
# ******************************************************************

sub copyFiles {
  my($files)   = shift;
  my($insdir)  = shift;
  my($verbose) = shift;

  foreach my $file (@$files) {
    my($dest) = $insdir . '/' .
                (defined $actual{$file} ?
                         "$actual{$file}/" . basename($file) : $file);
    my($fulldir) = dirname($dest);
    if (! -d $fulldir) {
      my($tmp) = '';
      foreach my $part (split(/[\/\\]/, $fulldir)) {
        $tmp .= $part . '/';
        mkdir($tmp, 0755);
      }
    }

    if (! -e $dest || (-M $file) < (-M $dest)) {
      if ($verbose) {
        print "Copying to $dest\n";
      }
      if (!copy($file, $dest)) {
        print STDERR "ERROR: Unable to copy $file to $dest\n";
        if (!$keepgoing) {
          return 0;
        }
      }
    }
    else {
      if ($verbose) {
        print "Skipping $file\n";
      }
    }
  }
  return 1;
}


sub determineSpecialName {
  my($tag)  = shift;
  my($dir)  = shift;
  my($info) = shift;

  my($insdir, $name) = split(/\s+/, $info);
  if (defined $name) {
    $insdir .= '/';
  }
  else {
    $name = $insdir;
    $insdir = '';
  }

  my($odir) = ($dir eq '' ? '.' : $dir) . '/' . $insdir;
  if ($tag eq 'exe_output') {
    my(@exes) = ();
    my($fh)   = new FileHandle();
    if (opendir($fh, $odir)) {
      foreach my $file (grep(!/^\.\.?$/, readdir($fh))) {
        if ($file =~ /^$name$/ ||
            $file =~ /^$name.*\.exe$/i) {
          push(@exes, "$dir$insdir$file");
        }
      }
      closedir($fh);
    }
    return @exes;
  }
  elsif ($tag eq 'lib_output') {
    my(@libs) = ();
    my($fh)   = new FileHandle();
    if (opendir($fh, $odir)) {
      foreach my $file (grep(!/^\.\.?$/, readdir($fh))) {
        if ($file =~ /^lib$name\.(a|so|sl)/ ||
            $file =~ /^$name.*\.(dll|lib)$/i) {
          push(@libs, "$dir$insdir$file");
        }
      }
      closedir($fh);
    }
    return @libs;
  }

  return "$dir$name";
}


sub replaceVariables {
  my($line) = shift;
  while($line =~ /(\$\(([^)]+)\))/) {
    my($whole) = $1;
    my($name)  = $2;
    my($val)   = (defined $ENV{$name} ? $ENV{$name} : '');
    $line =~ s/\$\([^)]+\)/$val/;
  }
  return $line;
}


sub loadInsFiles {
  my($files)   = shift;
  my($tags)    = shift;
  my($verbose) = shift;
  my($fh)      = new FileHandle();
  my(@copy)    = ();

  foreach my $file (@$files) {
    if (open($fh, $file)) {
      if ($verbose) {
        print "Loading $file\n";
      }
      my($base) = dirname($file);
      if ($base eq '.') {
        $base = '';
      }
      else {
        $base =~ s/^\.[\/\\]+//;
        $base .= '/';
      }

      my($current) = undef;
      while(<$fh>) {
        my($line) = $_;
        $line =~ s/^\s+//;
        $line =~ s/\s+$//;

        if ($line ne '') {
          if ($line =~ /^(\w+):$/) {
            if (defined $$tags{$1}) {
              $current = $1;
            }
            else {
              $current = undef;
            }
          }
          elsif (defined $current) {
            $line = replaceVariables($line);
            my($start) = $#copy + 1;
            if (defined $special{$current}) {
              push(@copy, determineSpecialName($current, $base, $line));
            }
            else {
              push(@copy, "$base$line");
            }
            if (defined $override{$current}) {
              for(my $i = $start; $i <= $#copy; ++$i) {
                $actual{$copy[$i]} = $override{$current};
              }
            }
            elsif (defined $base{$current}) {
              for(my $i = $start; $i <= $#copy; ++$i) {
                $actual{$copy[$i]} = $base{$current} . '/' .
                                     dirname($copy[$i]);
              }
            }
          }
        }
      }
      close($fh);
    }
    else {
      print STDERR "Unable to open $file\n";
      return ();
    }
  }

  return @copy;
}


sub getInsFiles {
  my($file)  = shift;
  my(@files) = ();

  if (-d $file) {
    my($fh) = new FileHandle();
    if (opendir($fh, $file)) {
      foreach my $f (grep(!/^\.\.?$/, readdir($fh))) {
        push(@files, getInsFiles("$file/$f"));
      }
      closedir($fh);
    }
  }
  elsif ($file =~ /\.$insext$/) {
    push(@files, $file);
  }
  return @files;
}


sub usageAndExit {
  my($msg) = shift;
  if (defined $msg) {
    print STDERR "$msg\n";
  }
  my($base) = basename($0);
  my($spc)  = ' ' x (length($base) + 8);
  print STDERR "$base v$version\n",
               "Usage: $base [-a tag1[,tagN]] [-b tag=dir] [-o tag=dir]\n",
               $spc, "[-s tag1[,tagN]] [-v] [install directory]\n",
               $spc, "[$insext files or directories]\n\n",
               "Install files matching the tag specifications found ",
               "in $insext files.\n\n",
               "-a  Adds to the default set of tags that get copied.\n",
               "-b  Install tag into dir underneath the install directory.\n",
               "-o  Install tag into dir.\n",
               "-s  Sets the tags that get copied.\n",
               "-v  Enables verbose mode.\n",
               "\n",
               "The default set of tags are:\n";
  my($first) = 1;
  foreach my $key (sort keys %defaults) {
    print STDERR '', ($first ? '' : ', '), $key;
    $first = 0;
  }
  print STDERR "\n";

  exit(0);
}

# ******************************************************************
# Main Section
# ******************************************************************

my($verbose)  = undef;
my($first)    = 1;
my($insdir)   = undef;
my(@insfiles) = ();
my(%tags)     = %defaults;

for(my $i = 0; $i <= $#ARGV; ++$i) {
  my($arg) = $ARGV[$i];
  if ($arg =~ /^-/) {
    if ($arg eq '-a') {
      ++$i;
      if (defined $ARGV[$i]) {
        foreach my $tag (split(',', $ARGV[$i])) {
          $tags{$tag} = 1;
        }
      }
      else {
        usageAndExit('-a requires a parameter.');
      }
    }
    elsif ($arg eq '-b') {
      ++$i;
      if (defined $ARGV[$i]) {
        if ($ARGV[$i] =~ /([^=]+)=(.*)/) {
          $base{$1} = $2;
        }
        else {
          usageAndExit("Invalid parameter to -b: $ARGV[$i]");
        }
      }
      else {
        usageAndExit('-b requires a parameter.');
      }
    }
    elsif ($arg eq '-k') {
      $keepgoing = 1;
    }
    elsif ($arg eq '-o') {
      ++$i;
      if (defined $ARGV[$i]) {
        if ($ARGV[$i] =~ /([^=]+)=(.*)/) {
          $override{$1} = $2;
        }
        else {
          usageAndExit("Invalid parameter to -o: $ARGV[$i]");
        }
      }
      else {
        usageAndExit('-o requires a parameter.');
      }
    }
    elsif ($arg eq '-s') {
      ++$i;
      if (defined $ARGV[$i]) {
        %tags = ();
        foreach my $tag (split(',', $ARGV[$i])) {
          $tags{$tag} = 1;
        }
      }
      else {
        usageAndExit('-s requires a parameter.');
      }
    }
    elsif ($arg eq '-v') {
      $verbose = 1;
    }
    else {
      usageAndExit('Unkown option: ' . $arg);
    }
  }
  elsif (!defined $insdir) {
    $arg =~ s/\\/\//g;
    $insdir = $arg;
  }
  else {
    if ($first) {
      $first = 0;
      if ($verbose) {
        print "Collecting $insext files...\n";
      }
    }
    $arg =~ s/\\/\//g;
    push(@insfiles, getInsFiles($arg));
  }
}

if (!defined $insdir) {
  usageAndExit();
}
elsif (!defined $insfiles[0]) {
  print "No $insext files were found.\n";
  exit(1);
}

my($status) = 1;
my(@files)  = loadInsFiles(\@insfiles, \%tags, $verbose);
if (defined $files[0]) {
  $status = (copyFiles(\@files, $insdir, $verbose) ? 0 : 1);
}

exit($status);
