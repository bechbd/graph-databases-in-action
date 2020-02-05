//Adds a person vertex with a name of Kelly and saves it to a variable
kelly = g.addV('person').property('first_name', 'Kelly').next()
//Adds a person vertex with a name of Jim and saves it to a variable
jim = g.addV('person').property('first_name', 'Jim').next()
//Adds a person vertex with a name of Paras and saves it to a variable
paras = g.addV('person').property('first_name', 'Paras').next()
//Adds a person vertex with a name of Denise and saves it to a variable
denise = g.addV('person').property('first_name', 'Denise').next()

//Adds friends edges
g.addE('friends').from(dave).to(jim).
        addE('friends').from(dave).to(kelly).
        addE('friends').from(kelly).to(jim).
        addE('friends').from(kelly).to(denise).
        addE('friends').from(jim).to(denise).
        addE('friends').from(jim).to(paras).
        addE('friends').from(paras).to(denise).iterate()
