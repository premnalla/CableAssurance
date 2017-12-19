Summary: opensnmp - A C++ SNMP toolkit
Name: opensnmp
Version: 0.4.2
Release: 1
License: BSD
Group: Development/Libraries
URL: http://www.opensnmp.com/
Packager: The OpenSNMP developers
Source0: %{name}-%{version}.tar.gz
# NOTE: turn on BuildRoot when perl module relocatable install works
BuildRoot: %{_tmppath}/%{name}-%{version}-buildroot
Requires: db4, openssl, libstdc++
# because libsmi doesn't come in rpm form dependencies will never succeed:
AutoReqProv: no

%description
OpenSNMP is a SNMPv3 C++ development toolkit which supports
multithreading and an extensible and modular architecture.

%package devel
Group: Development/Libraries
Summary: The includes and static libraries from the OpeSNMP package

%description devel
Development headers and libraries.

###############################################################################
%prep
%setup -q

###############################################################################
%build
%configure
make

###############################################################################
%install
rm -rf $RPM_BUILD_ROOT

%makeinstall

###############################################################################
%clean
rm -rf $RPM_BUILD_ROOT

###############################################################################
%files
%defattr(-,root,root,-)

%doc INSTALL LICENSE README

# modules

# binaries
%{_bindir}/cg
%{_bindir}/cr

%files devel
%{_libdir}/libopensnmp.a
%{_includedir}/opensnmp/*

###############################################################################
%changelog
* Fri Sep 12 2003 Wes Hardaker <hardaker@users.sourceforge.net>
- created.


