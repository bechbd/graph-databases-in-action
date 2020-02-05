//Adds a person vertex with a name of Kelly and saves it to a variable
kelly = g.addV('person').property('first_name', 'Kelly').next()
//Adds a person vertex with a name of Josh and saves it to a variable
jim = g.addV('person').property('first_name', 'Jim').next()
//Adds a person vertex with a name of Paras and saves it to a variable
paras = g.addV('person').property('first_name', 'Paras').next()
//Adds a person vertex with a name of Denise and saves it to a variable
denise = g.addV('person').property('first_name', 'Denise').next()

//Adds a friends edge between Dave and Jim
g.addE('friends').from(dave).to(jim).next()
//Adds a friends edge between Dave and Kelly
g.addE('friends').from(dave).to(kelly).next()
//Adds a friends edge between Kelly and Jim
g.addE('friends').from(kelly).to(jim).next()
//Adds a friends edge between Kelly and Denise
g.addE('friends').from(kelly).to(denise).next()
//Adds a friends edge between Jim and Denise
g.addE('friends').from(jim).to(denise).next()
//Adds a friends edge between Jim and Paras
g.addE('friends').from(jim).to(paras).next()
//Adds a friends edge between Paras and Denise
g.addE('friends').from(paras).to(denise).next()