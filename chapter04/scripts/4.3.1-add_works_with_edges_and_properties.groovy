//Connects the Gremlin Session to the "remote" Gremlin Server
//and runs this in a session instead of the default "sessionless" mode
:remote connect tinkerpop.server conf/remote.yaml session
//Sets Gremlin Console into "remote" mode so that each command is run on the server
:remote console

//Remove any existing data to allow this to be rerun
g.V().drop().iterate()

//Adds a person vertex with a name of Dave and saves it to a variable
dave = g.addV('person').property('first_name', 'Dave').next()
//Adds a person vertex with a name of Josh and saves it to a variable
josh = g.addV('person').property('first_name', 'Josh').next()
//Adds a person vertex with a name of Ted and saves it to a variable
ted = g.addV('person').property('first_name', 'Ted').next()
//Adds a person vertex with a name of Hank and saves it to a variable
hank = g.addV('person').property('first_name', 'Hank').next()

//Adds a friends edge between Dave and Ted
g.addE('friends').from(dave).to(ted).next()
//Adds a friends edge between Dave and Josh
g.addE('friends').from(dave).to(josh).next()
//Adds a friends edge between Dave and Hank
g.addE('friends').from(dave).to(hank).next()
//Adds a friends edge between Josh and Hank
g.addE('friends').from(josh).to(hank).next()
//Adds a friends edge between Ted and Josh
g.addE('friends').from(ted).to(josh).next()

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

//Adds works_with edges
g.addE('works_with').from(dave).to(josh).
    property('start_year',2016).property('end_year',2017).
  addE('works_with').from(dave).to(ted).
    property('start_year',2016).property('end_year',2017).
  addE('works_with').from(josh).to(ted).
    property('start_year',2016).property('end_year',2019).
  addE('works_with').from(dave).to(hank).
    property('start_year',2017).property('end_year',2018).
  addE('works_with').from(dave).to(kelly).
    property('start_year',2018).
  addE('works_with').from(dave).to(denise).
    property('start_year',2018).
  addE('works_with').from(denise).to(kelly).
    property('start_year',2018).iterate()