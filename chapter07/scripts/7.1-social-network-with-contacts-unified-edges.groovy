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
ted_phone = g.addV('contact').property('number', '555-1212').property('type', 'phone').next()
ted_email = g.addV('contact').property('address', 'fake@fake.com').property('type', 'email').next()
ted_fax = g.addV('contact').property('number', '555-1213').property('type', 'fax').next()

// add connections
g.addE('contact_by').from(ted).to(ted_phone).next()
g.addE('contact_by').from(ted).to(ted_email).next()
g.addE('contact_by').from(ted).to(ted_fax).next()
