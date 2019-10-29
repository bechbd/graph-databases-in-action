:remote connect tinkerpop.server conf/remote.yaml session
:remote console

g.V().drop().iterate()

dave = g.addV('person').property('first_name','Dave').next()
josh = g.addV('person').property('first_name','Josh').next()
ted = g.addV('person').property('first_name','Ted').next()
hank = g.addV('person').property('first_name','Hank').next()

g.addE('friends').from(dave).to(ted).
  addE('friends').from(dave).to(josh).
  addE('friends').from(dave).to(hank).
  addE('friends').from(josh).to(hank).
  addE('friends').from(ted).to(josh).iterate()
