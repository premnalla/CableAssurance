package VC71WorkspaceCreator;

# ************************************************************
# Description   : A VC7.1 Workspace Creator
# Author        : Chad Elliott
# Create Date   : 4/17/2003
# ************************************************************

# ************************************************************
# Pragmas
# ************************************************************

use strict;

use VC71ProjectCreator;
use VC7WorkspaceCreator;

use vars qw(@ISA);
@ISA = qw(VC7WorkspaceCreator);

# ************************************************************
# Subroutine Section
# ************************************************************


sub pre_workspace {
  my($self) = shift;
  my($fh)   = shift;
  my($crlf) = $self->crlf();

  print $fh 'Microsoft Visual Studio Solution File, Format Version 8.00', $crlf,
            '#', $crlf,
            '# VC71WorkspaceCreator.pm,v 1.7 2006/01/25 17:42:09 elliott_c Exp', $crlf,
            '#', $crlf,
            '# This file was generated by MPC.  Any changes made directly to', $crlf,
            '# this file will be lost the next time it is generated.', $crlf,
            '#', $crlf,
            '# MPC Command:', $crlf,
            "# $0 @ARGV", $crlf;
}


sub print_inner_project {
  my($self)  = shift;
  my($fh)    = shift;
  my($gen)   = shift;
  my($pguid) = shift;
  my($deps)  = shift;
  my($project_name) = shift;
  my($name_to_guid_map) = shift;

  if ($self->allow_empty_dependencies() || defined $$deps[0]) {
    my($crlf) = $self->crlf();
    print $fh "\tProjectSection(ProjectDependencies) = postProject$crlf";
    foreach my $dep (@$deps) {
      my($guid) = $name_to_guid_map->{$dep};
      if (defined $guid) {
        print $fh "\t\t{$guid} = {$guid}$crlf";
      }
    }
    print $fh "\tEndProjectSection$crlf";
  }
}


sub allow_empty_dependencies {
  #my($self) = shift;
  return 1;
}


sub print_configs {
  my($self)    = shift;
  my($fh)      = shift;
  my($configs) = shift;
  my($crlf)    = $self->crlf();
  foreach my $key (sort keys %$configs) {
    print $fh "\t\t$key = $key$crlf";
  }
}


sub print_dependencies {
  ## These are done in the print_inner_project method
}


1;
