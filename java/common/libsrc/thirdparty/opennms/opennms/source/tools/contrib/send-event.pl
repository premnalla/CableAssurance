#!@install.perl.bin@

use strict;
use Getopt::Mixed "nextOption";
use IO::Socket;
use POSIX qw(strftime);
use vars qw(
	$VERSION

	$DESCR
	$HOSTNAME
	$INTERFACE
	$NODEID
	$SERVICE
	$SEVERITY
	$SOURCE
	$UEI
	$VERBOSE
	$ZONE
	$OPERINSTR
	@PARMS

	@SEVERITIES
	$HOST_TO
	$PORT_TO
);

$VERSION = '0.2';
$VERBOSE = 0;
$ZONE    = 'GMT';

@SEVERITIES = ( undef, 'Indeterminate', 'Cleared', 'Normal', 'Warning', 'Minor', 'Major', 'Critical' );
	
Getopt::Mixed::init('h help>h d=s descr>d i=s interface>i n=i nodeid>n p=s parm>p s=s service>s t=s  u=s uei>u V version>V v verbose>v x=s severity>x o=s operinstr>o');
while (my ($option, $value) = nextOption()) {

        $SERVICE   = $value if ($option eq "s");
        $NODEID    = $value if ($option eq "n");
	$INTERFACE = $value if ($option eq "i");
        $DESCR     = $value if ($option eq "d");
        $SEVERITY  = $value if ($option eq "x");
	$VERBOSE   = 1      if ($option eq "v");
	$OPERINSTR = $value if ($option eq "o");
	push(@PARMS, parse_parm($value)) if ($option eq "p");

	if ($option eq "V") { print "$0 version $VERSION\n"; exit; }
	if ($option eq "h") { print get_help(); exit; }

}
Getopt::Mixed::cleanup();

chomp (my $hostname = `hostname`);
my @addr = gethostbyname($hostname);

$SOURCE   = 'perl_send_event';
$HOSTNAME = gethostbyaddr($addr[4], 2);
$UEI      = $ARGV[0];
$HOST_TO  = $ARGV[1];
$PORT_TO  = 5817;

#### bounds-checking on various inputs

# UEI
if (defined $UEI) {
	unless (grep(m#uei#, $UEI)) {
		print "*** \"$UEI\" does not appear to be a valid UEI\n\n";
		print get_help();
		exit 1;
	}
} else {
	print get_banner(), "the UEI is a required field!\n";
	print get_help();
	exit 1;
}

if (defined $HOST_TO) {
	my ($host, $port) = split(/:/, $HOST_TO);
	if ($port =~ /^\d+$/ and $port > 0) {
		$PORT_TO = $port;
	}
	if ($host ne "") {
		$HOST_TO = $host;
	}
} else {
	$HOST_TO = 'localhost';
}

if (defined $SEVERITY) {
	my $SEVERITY_OK = 0;
	if ($SEVERITY !~ /^\d+$/) {
		$SEVERITY = ucfirst(lc($SEVERITY));
		for my $index (0..$#SEVERITIES) {
			if ($SEVERITY eq $SEVERITIES[$index]) {
				$SEVERITY_OK = 1;
				last;
			}
		}
		unless ($SEVERITY_OK) {
			print "*** $SEVERITY does not appear to be a valid severity level\n\n";
			print get_help();
			exit 1;
		}
	} else {
		if (defined $SEVERITIES[$SEVERITY]) {
			$SEVERITY = $SEVERITIES[$SEVERITY];
		} else {
			print "*** $SEVERITY does not appear to be a valid severity level\n\n";
			print get_help();
			exit 1;
		}
	}
}

if (defined $INTERFACE) {
	unless (4 == grep($_ <= 255, $INTERFACE =~ /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/)) {
		print "*** \"$INTERFACE\" does not appear to be a valid IP address\n\n";
		print get_help();
		exit 1;
	}
}

if (defined $DESCR) {
	($DESCR) = simple_parse($DESCR);
}

if (defined $SERVICE) {
	($SERVICE) = simple_parse($SERVICE);
}

my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = gmtime(time);
$year += 1900;
my $month = $mon;
$min   = sprintf("%02d", $min);
$sec   = sprintf("%02d", $sec);
my $ap = "AM";
$ap    = "PM" if ($hour >= 12);
$hour  = $hour % 12;
my @week = ('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
my @month = ('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');

my $event = <<END;
<log>
 <events>
  <event>
   <source>$SOURCE</source>
   <host>$HOSTNAME</host>
   <time>$week[$wday], $month[$month] $mday, $year $hour:$min:$sec $ap $ZONE</time>
   <uei>$UEI</uei>
END

$event .= "   <nodeid>$NODEID</nodeid>\n"          if (defined $NODEID);
$event .= "   <interface>$INTERFACE</interface>\n" if (defined $INTERFACE);
$event .= "   <service>$SERVICE</service>\n"       if (defined $SERVICE);
$event .= "   <descr>$DESCR</descr>\n"             if (defined $DESCR);
$event .= "   <severity>$SEVERITY</severity>\n"    if (defined $SEVERITY);
$event .= "   <operinstruct>$OPERINSTR</operinstruct>\n" if (defined $OPERINSTR);

if (@PARMS) {
  $event .= "   <parms>\n";
  for my $parm (@PARMS) {
    $event .= <<END;
    <parm>
     <parmName><![CDATA[$parm->{'name'}]]></parmName>
     <value type="string" encoding="text"><![CDATA[$parm->{'value'}]]></value>
    </parm>
END
  }
  $event .= "   </parms>\n";
}

$event .= <<END;
  </event>
 </events>
</log>
END

print "- sending to $HOST_TO on port $PORT_TO...\n" if ($VERBOSE);

my $socket = IO::Socket::INET->new(PeerAddr => $HOST_TO, PeerPort => $PORT_TO, Proto => "tcp", Type => SOCK_STREAM)
	or die "Couldn't connect to $HOST_TO:$PORT_TO - $@\n";

print "$event" if ($VERBOSE);
print $socket $event;
$socket->close();

sub parse_parm {
  my $parm = shift;

  my ($name, $value) = split(/\s+/, $parm, 2);
  return ({ name => $name, value => $value });
}

sub get_banner {
	return <<END;
Usage: $0 <UEI> [host] [options]

END
}

sub simple_parse {
	for (@_) {
		s#\&#\&amp;#gs;
		s#\<#\&lt;#gs;
		s#\>#\&gt;#gs;
		s#\'#\&apos;#gs;
		s#\"#\&quot;#gs;
	}
	return @_;
}

sub get_help {
	return (get_banner, <<END);

Options:

         <UEI>             the universal event identifier (URI)
         [host[:port]]     a hostname to send the event to (default: localhost)
         --version, -V     print version and exit successfully
         --verbose, -v     print the raw XML that's generated
         --help, -h        this help message

         --timezone, -t    the time zone you are in
         --service, -s     service name 
         --nodeid, -n      node identifier (numeric)
         --interface, -i   IP address of the interface
         --descr, -d       a description for the event browser
         --severity, -x    the severity of the event (numeric or name)
                           1 = Indeterminate
                           2 = Cleared (unimplemented at this time)
                           3 = Normal
                           4 = Warning
                           5 = Minor
                           6 = Major
                           7 = Critical
	--parm, -p         an event parameter (ie:
                           --parm 'url http://www.google.com/')
Example:

        Force discovery of a node

        send-event.pl \\
        --interface 172.16.1.1
        uei.opennms.org/internal/discovery/newSuspect \\

END
}
