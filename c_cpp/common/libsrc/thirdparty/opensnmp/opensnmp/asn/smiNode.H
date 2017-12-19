#ifndef SMINODE_H
#define SMINODE_H

#include "OID.H"

//
// smiNode is an interface on top of an OID class to retrieve SMI
// related data (descriptions, children, ...)
//

class smiNode : public OID {
private:
  
protected:
  SmiNode *get_sminode();
  
public:
  smiNode();
  smiNode(unsigned int *init, int initlen);
  smiNode(const char *in);
  smiNode(const OID &thecopy, char *append = 0);
  smiNode(std::list<unsigned int> fromlist);
  smiNode(SmiNode *sn);
  virtual ~smiNode();
  
  virtual std::string label(void);
  virtual std::string description(void);
  virtual std::string access(void);
  virtual std::string status(void);
  virtual std::string nodekind(void);
  virtual std::string indexkind(void);
  virtual std::string smitypeid(void);
  virtual std::string decl(void);
  virtual std::string implied(void);
  virtual std::string format(void);
  virtual std::string units(void);

  // static conversion function to convert std::string from
  // 'string basetype()' back to type SmiBasetype.
  static  SmiBasetype stringToBasetype(string bTypeStr);
  virtual std::string      basetype(void);

  virtual std::list<string> indexes(void);

  virtual bool is_scalar(void);
  virtual bool is_row(void);
  virtual bool is_table(void);
  virtual bool is_column(void);
  virtual bool is_access_readcreate_readwrite(void);
  virtual bool is_implied(void);

  // smi direct accesses
  SmiStatus     get_smiStatus(void);
  SmiAccess     get_smiAccess(void);
  SmiNodekind   get_smiNodekind(void);
  SmiIndexkind  get_smiIndexkind(void);
  SmiElement   *get_smiElement(void);
  SmiBasetype   get_smiBasetype(void);
  SmiDecl       get_smiDecl(void);
  int           get_smiImplied(void);
 
  virtual std::list<smiNode *> get_children(void);
  virtual std::list<smiNode *> get_indexes(void);

};

#endif /* SMINODE_H */
