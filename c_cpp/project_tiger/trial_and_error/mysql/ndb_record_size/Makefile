TARGET = ndb_record_size
SRCS = $(TARGET).cpp
OBJS = $(TARGET).o
ARCH = -march=x86-64
CXX = g++
CFLAGS = -c -Wall -fno-rtti -fno-exceptions $(ARCH)
CXXFLAGS =
DEBUG =
LFLAGS = -Wall $(ARCH)
INSTALL_DIR = /home/prem/mysql/
INCLUDE_DIR = $(INSTALL_DIR)/include/mysql
LIB_DIR = -L$(INSTALL_DIR)/lib/mysql
SYS_LIB = -lrt

$(TARGET): $(OBJS)
	$(CXX) $(CXXFLAGS) $(LFLAGS) $(LIB_DIR) $(OBJS) -lndbclient -lmysqlclient_r -lmysys -lmystrings  -lz $(SYS_LIB) -o $(TARGET)

$(TARGET).o: $(SRCS)
	 $(CXX) $(CFLAGS) -I$(INCLUDE_DIR) -I$(INCLUDE_DIR)/storage/ndb/ $(SRCS) -I$(INCLUDE_DIR)/storage/ndb/ndbapi $(SRCS)

clean:
	rm -rf ndb_record_size
	rm -rf ndb_record_size.o
	rm -rf core.*
	rm -rf *~
