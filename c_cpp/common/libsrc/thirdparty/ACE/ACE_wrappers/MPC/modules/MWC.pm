package MWC;

# ******************************************************************
# Description : Instantiate a Driver and run it
# Author      : Chad Elliott
# Create Date : 1/30/2004
# ******************************************************************

# ******************************************************************
# Pragma Section
# ******************************************************************

use strict;
use Driver;

# ************************************************************
# Subroutine Section
# ************************************************************

sub new {
  my($class) = shift;
  my($self)  = bless {'creators' => [ 'MakeWorkspaceCreator',
                                      'NMakeWorkspaceCreator',
                                      'VC6WorkspaceCreator',
                                      'VC7WorkspaceCreator',
                                      'VC71WorkspaceCreator',
                                      'VC8WorkspaceCreator',
                                      'CBXWorkspaceCreator',
                                      'BDSWorkspaceCreator',
                                      'GHSWorkspaceCreator',
                                      'EM3WorkspaceCreator',
                                      'AutomakeWorkspaceCreator',
                                      'BMakeWorkspaceCreator',
                                      'HTMLWorkspaceCreator',
                                      'SLEWorkspaceCreator',
                                    ],
                     }, $class;
  return $self;
}


sub getCreatorList {
  my($self) = shift;
  return $self->{'creators'};
}


sub execute {
  my($self)   = shift;
  my($base)   = shift;
  my($name)   = shift;
  my($args)   = shift;
  my($driver) = new Driver($base, $name, @{$self->{'creators'}});
  return $driver->run(@$args);
}


1;
