
include makeinclude.mk

SRVR_PROGRAM = TcpServer
CLNT_PROGRAM = TcpClient

PROGRAMS = $(SRVR_PROGRAM) $(CLNT_PROGRAM)

INCLUDES = \
	-Iinclude

LIB_DIR = \
	-Llib

DEBUG = -g

OTHER = 

CPPFLAGS = $(INCLUDES) $(DEBUG) $(OTHER)

SRVR_SRC = \
	Common.cpp \
	ServerMain.cpp \
#

CLNT_SRC = \
	Common.cpp \
	ClientMain.cpp \
#

SRVR_OBJ = $(SRVR_SRC:%.cpp=%.o)
CLNT_OBJ = $(CLNT_SRC:%.cpp=%.o)

LDFLAGS = \
	-lpthread \
	-lposix4 \
	-lsocket \
	-lnsl \
#	-lxnet \
#	-lm \
#

all: $(PROGRAMS)

$(SRVR_PROGRAM) : $(SRVR_OBJ)
	@echo "linking $(SRVR_PROGRAM) ..."
	$(CCC) -o $(SRVR_PROGRAM) $(CPPFLAGS) $(LDFLAGS) $(SRVR_OBJ)

$(CLNT_PROGRAM) : $(CLNT_OBJ)
	@echo "linking $(CLNT_PROGRAM) ..."
	$(CCC) -o $(CLNT_PROGRAM) $(CPPFLAGS) $(LDFLAGS) $(CLNT_OBJ)

.cpp.o :
	$(CCC) $(CPPFLAGS) -c -o $(@:%.c=%.o) $<

clean:
	rm *.o

