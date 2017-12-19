package MakeWorkspaceCreator;

# ************************************************************
# Description   : A Generic Workspace (Makefile) creator
# Author        : Chad Elliott
# Create Date   : 2/18/2003
# ************************************************************

# ************************************************************
# Pragmas
# ************************************************************

use strict;
use File::Basename;

use MakeProjectCreator;
use WorkspaceCreator;

use vars qw(@ISA);
@ISA = qw(WorkspaceCreator);

# ************************************************************
# Data Section
# ************************************************************

my(@targets) = ('clean', 'depend', 'generated', 'realclean',
                '$(CUSTOM_TARGETS)');

# ************************************************************
# Subroutine Section
# ************************************************************

sub workspace_file_name {
  my($self) = shift;
  return $self->get_modified_workspace_name('Makefile', '');
}


sub workspace_per_project {
  #my($self) = shift;
  return 1;
}


sub pre_workspace {
  my($self) = shift;
  my($fh)   = shift;
  my($crlf) = $self->crlf();

  print $fh '#----------------------------------------------------------------------------', $crlf,
            '#       Make Workspace', $crlf,
            '#', $crlf,
            '# MakeWorkspaceCreator.pm,v 1.18 2006/01/23 18:09:34 elliott_c Exp', $crlf,
            '#', $crlf,
            '# This file was generated by MPC.  Any changes made directly to', $crlf,
            '# this file will be lost the next time it is generated.', $crlf,
            '#', $crlf,
            '# MPC Command:', $crlf,
            "# $0 @ARGV", $crlf,
            '#----------------------------------------------------------------------------', $crlf,
            $crlf;
}


sub write_comps {
  my($self)     = shift;
  my($fh)       = shift;
  my($crlf)     = $self->crlf();
  my($projects) = $self->get_projects();
  my($trans)    = $self->project_target_translation(1);
  my(%targnum)  = ();
  my($pjs)      = $self->get_project_info();
  my(@list)     = $self->number_target_deps($projects, $pjs, \%targnum, 0);

  ## Print out the "all" target
  print $fh $crlf . 'all:';
  foreach my $project (@list) {
    print $fh " $$trans{$project}";
  }

  ## Print out all other targets here
  print $fh "$crlf$crlf@targets:$crlf";
  foreach my $project (@list) {
    my($dname) = $self->mpc_dirname($project);
    print $fh "\t\@",
              ($dname ne '.' ? "cd $dname; " : ''),
              "\$(MAKE) -f ",
              ($dname eq '.' ? $project : basename($project)),
              " \$(\@)$crlf";
  }

  ## Print out each target separately
  foreach my $project (@list) {
    my($dname) = $self->mpc_dirname($project);
    print $fh $crlf, '.PHONY: ', $$trans{$project},
              $crlf, $$trans{$project}, ':';
    if (defined $targnum{$project}) {
      foreach my $number (@{$targnum{$project}}) {
        print $fh " $$trans{$list[$number]}";
      }
    }

    print $fh $crlf,
              "\t\@",
              ($dname ne '.' ? "cd $dname; " : ''),
              "\$(MAKE) -f ",
              ($dname eq '.' ? $project : basename($project)),
              ' generated all', $crlf;
  }

  ## Print out the project_name_list target
  print $fh $crlf, "project_name_list:$crlf";
  foreach my $project (sort @list) {
    print $fh "\t\@echo $$trans{$project}$crlf";
  }
}



1;
