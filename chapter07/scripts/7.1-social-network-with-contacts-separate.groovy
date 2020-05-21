//Connects the Gremlin Session to the "remote" Gremlin Server
//and runs this in a session instead of the default "sessionless" mode
:remote connect tinkerpop.server conf/remote.yaml session
//Sets Gremlin Console into "remote" mode so that each command is run on the server
:remote console

//Remove any existing data to allow this to be rerun
g.V().drop().iterate()

//Adds a person vertex with a name of Ted and saves it to a variable
ted = g.addV('person').property('name', 'Ted').next()

// add contact details for Ted
ted_phone = g.addV('phone').property('number', '555-1212').next()
ted_email = g.addV('email').property('address', 'fake@fake.com').next()
ted_fax = g.addV('fax').property('number', '555-1213').next()

// add connections
g.addE('has_phone').from(ted).to(ted_phone).next()
g.addE('has_email').from(ted).to(ted_email).next()
g.addE('has_fax').from(ted).to(ted_fax).next()
