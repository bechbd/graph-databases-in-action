//Adds a person vertex with a name of Kelly and saves it to a variable
kelly = g.addV('person').property('name', 'Kelly').next()
//Adds a person vertex with a name of Josh and saves it to a variable
jim = g.addV('person').property('name', 'Jim').next()
//Adds a person vertex with a name of Paras and saves it to a variable
paras = g.addV('person').property('name', 'Paras').next()
//Adds a person vertex with a name of Denise and saves it to a variable
denise = g.addV('person').property('name', 'Denise').next()

//Adds a is_friends_with edge between Dave and Jim
g.addE('is_friends_with').from(dave).to(jim).next()
//Adds a is_friends_with edge between Dave and Kelly
g.addE('is_friends_with').from(dave).to(kelly).next()
//Adds a is_friends_with edge between Kelly and Jim
g.addE('is_friends_with').from(kelly).to(jim).next()
//Adds a is_friends_with edge between Kelly and Denise
g.addE('is_friends_with').from(kelly).to(denise).next()
//Adds a is_friends_with edge between Jim and Denise
g.addE('is_friends_with').from(jim).to(denise).next()
//Adds a is_friends_with edge between Jim and Paras
g.addE('is_friends_with').from(jim).to(paras).next()
//Adds a is_friends_with edge between Paras and Denise
g.addE('is_friends_with').from(paras).to(denise).next()