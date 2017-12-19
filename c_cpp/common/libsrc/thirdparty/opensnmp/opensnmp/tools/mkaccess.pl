use Getopt::Std;

getopts("c:dnh:");

my $class = $opt_c;

$dir .= '/' if $dir and $dir !~ m!/$!;

my $header = $opt_h || "$class.H";

my $source;

($source = $header) =~ s/\.H$/\.C/;

open(FILE,"<$header") or die "Could not open header file: $header";

while (<FILE>) {
    next if /^\s*$/;

    if (/(public:|private:|protected:)/) {
	$access_on = 1, next if $1 eq 'private:';
	$access_on = 0, next;
    }

    if ($access_on) {
	if (/\s*([\w\s><\*]+?)\*\s*(\w+)\s*(=.*)?;/) {
	    $symbol = $2; $type = $1;
	    $type =~ s/\s+/ /g;
	    print "type:[$type] symbol:[$symbol]\n" if $opt_d;
	    push(@symbols,$symbol);
	    $symbols{$symbol} = "$type *";
	}
    }
}

foreach $symbol (@symbols) {
    print "virtual void set_$symbol($symbols{$symbol}$symbol);\n";
    print "virtual $symbols{$symbol}get_$symbol(bool extract=false);\n";
} 
print '-' x 79, "\n";
foreach $symbol (@symbols) {

    print <<END_IMPL;
void
${class}::set_$symbol($symbols{$symbol}$symbol) {
  if (this->$symbol)
    delete this->$symbol;
  this->$symbol = $symbol;
\}

$symbols{$symbol}
${class}::get_$symbol(bool extract=false) {
  $symbols{$symbol}ret = $symbol;
  if (extract)
    $symbol = NULL;
  return ret;
\}

END_IMPL
} 

