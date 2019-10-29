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

kelly = g.addV('person').property('first_name', 'Kelly').next()
jim = g.addV('person').property('first_name', 'Jim').next()
paras = g.addV('person').property('first_name', 'Paras').next()
denise = g.addV('person').property('first_name', 'Denise').next()

g.addE('friends').from(dave).to(jim).
  addE('friends').from(dave).to(kelly).
  addE('friends').from(kelly).to(jim).
  addE('friends').from(kelly).to(denise).
  addE('friends').from(jim).to(denise).
  addE('friends').from(jim).to(paras).
  addE('friends').from(paras).to(denise).iterate()

