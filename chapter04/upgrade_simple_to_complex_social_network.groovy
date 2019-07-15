//Adds a person vertex with a name of Kelly and saves it to a variable
kelly = g.addV('person').property('name', 'Kelly').next()
//Adds a person vertex with a name of Jim and saves it to a variable
jim = g.addV('person').property('name', 'Jim').next()
//Adds a person vertex with a name of Paras and saves it to a variable
paras = g.addV('person').property('name', 'Paras').next()
//Adds a person vertex with a name of Denise and saves it to a variable
denise = g.addV('person').property('name', 'Denise').next()

//Adds is_friends_with edges
g.addE('is_friends_with').from(dave).to(jim).
        addE('is_friends_with').from(dave).to(kelly).
        addE('is_friends_with').from(kelly).to(jim).
        addE('is_friends_with').from(kelly).to(denise).
        addE('is_friends_with').from(jim).to(denise).
        addE('is_friends_with').from(jim).to(paras).
        addE('is_friends_with').from(paras).to(denise).iterate()
