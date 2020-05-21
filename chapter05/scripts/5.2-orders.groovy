//Connects the Gremlin Session to the "remote" Gremlin Server
//and runs this in a session instead of the default "sessionless" mode
:remote connect tinkerpop.server conf/remote.yaml session
//Sets Gremlin Console into "remote" mode so that each command is run on the server
:remote console

//Remove any existing data to allow this to be rerun
g.V().drop().iterate()

//Adds an order vertices 
abc123 = g.addV('order').property('number', 'ABC123').next()
def234 = g.addV('order').property('number', 'DEF234').next()
// add product vertices
product1 = g.addV('product').property('name', 'widget 1').next()
product2 = g.addV('product').property('name', 'widget 2').next()
product3 = g.addV('product').property('name', 'widget 3').next()

//Adds a contains edge between orders & products
g.addE('contains').from(abc123).to(product1).property('count', 5).next()
g.addE('contains').from(abc123).to(product2).property('count', 10).next()
g.addE('contains').from(def234).to(product2).property('count', 4).next()
g.addE('contains').from(def234).to(product3).property('count', 6).next()
