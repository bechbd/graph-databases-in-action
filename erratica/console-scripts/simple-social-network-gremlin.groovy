:remote connect tinkerpop.server conf/remote.yaml session
:remote console

g.V().drop().iterate()

dave = g.addV('person').property('name','Dave').next()
josh = g.addV('person').property('name','Josh').next()
ted = g.addV('person').property('name','Ted').next()
hank = g.addV('person').property('name','Hank').next()

g.addE('is_friends_with').from(dave).to(ted).iterate()

g.addE('is_friends_with').from(dave).to(josh).
  addE('is_friends_with').from(dave).to(hank).
  addE('is_friends_with').from(josh).to(hank).
  addE('is_friends_with').from(ted).to(josh).iterate()


