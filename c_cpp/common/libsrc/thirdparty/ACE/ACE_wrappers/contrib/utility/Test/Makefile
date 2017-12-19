# file      : Test/Makefile
# author    : Boris Kolpackov <boris@kolpackov.net>
# copyright : Copyright (c) 2002-2003 Boris Kolpackov
# license   : http://kolpackov.net/license.html

root := ..

include $(root)/BuildRules/Bootstrap.rules

$(call include, $(root)/BuildRules/Recursion.pre.rules)

target_makefile_list  :=
target_directory_list := ExH Introspection ReferenceCounting Synch

ReferenceCounting : ExH Synch

$(call include, $(root)/BuildRules/Recursion.post.rules)
# Makefile,v 1.1 2005/05/24 04:33:12 turkaye Exp
