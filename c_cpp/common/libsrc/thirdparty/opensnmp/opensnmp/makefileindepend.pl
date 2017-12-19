#!/usr/bin/perl

# Move dependencies from the Makefile to the Makefile.in file, saving
# a backup in Makefile.in.bak.

rename("Makefile.in","Makefile.in.bak");
open(F,"Makefile.in.bak");
open(G,"Makefile");
open(O,">Makefile.in"); 
$_ = <F>;
while(!/^\# DO NOT DELETE/) { 
    print O $_; 
    $_ = <F>; 
}
print O $_;
$_ = <G>; 
while(!/^\# DO NOT DELETE/) { 
    $_ = <G>; 
} 

while (<G>) {
    print O $_;
} 
