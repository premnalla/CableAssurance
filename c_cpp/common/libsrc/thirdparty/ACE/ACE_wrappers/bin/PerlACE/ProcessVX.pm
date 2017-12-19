# ProcessVX.pm,v 1.2 2005/04/12 07:28:15 jwillemsen Exp

package PerlACE::ProcessVX;

use strict;
use English;
use POSIX qw(:time_h);

$PerlACE::ProcessVX::ExeSubDir = './';

sub delay_factor {
  my($lps)    = 128;
  my($factor) = 1;

  ## Keep increasing the loops per second until the amount of time
  ## exceeds the number of clocks per second.  The original code
  ## did not multiply $ticks by 8 but, for faster machines, it doesn't
  ## seem to return false values.  The multiplication is done to minimize
  ## the amount of time it takes to determine the correct factor.
  while(($lps <<= 1)) {
    my($ticks) = clock();
    for(my $i = $lps; $i >= 0; $i--) {
    }
    $ticks = clock() - $ticks;
    if ($ticks * 8 >= CLOCKS_PER_SEC) {
      $factor = 500000 / (($lps / $ticks) * CLOCKS_PER_SEC);
      last;
    }
  }

  return $factor;
}

### Check for -ExeSubDir commands, store the last one
my @new_argv = ();

for(my $i = 0; $i <= $#ARGV; ++$i) {
    if ($ARGV[$i] eq '-ExeSubDir') {
        if (defined $ARGV[$i + 1]) {
            $PerlACE::ProcessVX::ExeSubDir = $ARGV[++$i].'/';
        }
        else {
            print STDERR "You must pass a directory with ExeSubDir\n";
            exit(1);
        }
    }
    else {
        push @new_argv, $ARGV[$i];
    }
}
@ARGV = @new_argv;

$PerlACE::ProcessVX::WAIT_DELAY_FACTOR = $ENV{"ACE_RUNTEST_DELAY"};

if ($OSNAME eq "MSWin32") {
	require PerlACE::ProcessVX_Win32;
}
else {
	# PerlACE::ProcessVX not supported on Unix yet!
	## require PerlACE::Process_Unix;
}

1;
