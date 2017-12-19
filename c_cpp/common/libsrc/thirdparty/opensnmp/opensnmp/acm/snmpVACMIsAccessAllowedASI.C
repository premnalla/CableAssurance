#include "config.h"
#include "snmpVACMIsAccessAllowedASI.H"

snmpVACMIsAccessAllowedASI::snmpVACMIsAccessAllowedASI(
    Integer32        *securityModel,
    OctetString      *securityName,
    Integer32        *securityLevel,
    viewType          theViewType,
    OctetString      *contextName,
    OID              *variableName,
    snmpStatusInfo   *newStatusInformation,
    snmpFIFOObj      *theFIFO,
    snmpObj          *stateReference) {

    this->securityModel = securityModel;
    this->securityName = securityName;
    this->securityLevel = securityLevel;
    this->theViewType = theViewType;
    this->contextName = contextName;
    this->variableName = variableName;
    this->statusInformation = newStatusInformation;
    this->theFIFO = theFIFO;
    this->stateReference = stateReference;
}

snmpVACMIsAccessAllowedASI::snmpVACMIsAccessAllowedASI(
    snmpVACMIsAccessAllowedASI &ref) {

    this->securityModel     = ref.securityModel->clone();
    this->securityName      = ref.securityName->clone();
    this->securityLevel     = ref.securityLevel->clone();
    this->theViewType       = ref.theViewType;
    this->contextName       = ref.contextName->clone();
    this->variableName      = ref.variableName->clone();
    this->statusInformation = ref.statusInformation->clone();
    this->theFIFO           = ref.theFIFO;
}

snmpVACMIsAccessAllowedASI::~snmpVACMIsAccessAllowedASI() {
    if (this->securityModel)         delete this->securityModel;
    if (this->securityName)          delete this->securityName;
    if (this->securityLevel)         delete this->securityLevel;
    if (this->contextName)           delete this->contextName;
    if (this->variableName)          delete this->variableName;
    if (this->statusInformation)     delete this->statusInformation;
}

// It wouldn't be C++ if it didn't have a billion lines of accessor code.

void
snmpVACMIsAccessAllowedASI::set_securityModel(Integer32  *securityModel) {
  if (this->securityModel)
    delete this->securityModel;
  this->securityModel = securityModel;
}

Integer32  *
snmpVACMIsAccessAllowedASI::get_securityModel(bool extract) {
  Integer32  *ret = securityModel;
  if (extract)
    securityModel = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_securityName(OctetString  *securityName) {
  if (this->securityName)
    delete this->securityName;
  this->securityName = securityName;
}

OctetString  *
snmpVACMIsAccessAllowedASI::get_securityName(bool extract) {
  OctetString  *ret = securityName;
  if (extract)
    securityName = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_securityLevel(Integer32  *securityLevel) {
  if (this->securityLevel)
    delete this->securityLevel;
  this->securityLevel = securityLevel;
}

Integer32  *
snmpVACMIsAccessAllowedASI::get_securityLevel(bool extract) {
  Integer32  *ret = securityLevel;
  if (extract)
    securityLevel = NULL;
  return ret;
}
void
snmpVACMIsAccessAllowedASI::set_contextName(OctetString  *contextName) {
  if (this->contextName)
    delete this->contextName;
  this->contextName = contextName;
}

OctetString  *
snmpVACMIsAccessAllowedASI::get_contextName(bool extract) {
  OctetString  *ret = contextName;
  if (extract)
    contextName = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_variableName(OID  *variableName) {
  if (this->variableName)
    delete this->variableName;
  this->variableName = variableName;
}

OID  *
snmpVACMIsAccessAllowedASI::get_variableName(bool extract) {
  OID  *ret = variableName;
  if (extract)
    variableName = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_statusInformation(snmpStatusInfo  *statusInformation) {
  if (this->statusInformation)
    delete this->statusInformation;
  this->statusInformation = statusInformation;
}

snmpStatusInfo  *
snmpVACMIsAccessAllowedASI::get_statusInformation(bool extract) {
  snmpStatusInfo  *ret = statusInformation;
  if (extract)
    statusInformation = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_stateReference(snmpObj  *stateReference) {
  if (this->stateReference)
    delete this->stateReference;
  this->stateReference = stateReference;
}

snmpObj  *
snmpVACMIsAccessAllowedASI::get_stateReference(bool extract) {
  snmpObj  *ret = stateReference;
  if (extract)
    stateReference = NULL;
  return ret;
}

void
snmpVACMIsAccessAllowedASI::set_viewType(viewType viewtype) {
    this->theViewType = viewtype;
}

viewType snmpVACMIsAccessAllowedASI::get_viewType() {
    return this->theViewType;
}


