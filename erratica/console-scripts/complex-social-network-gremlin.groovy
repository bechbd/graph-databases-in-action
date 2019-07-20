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

kelly = g.addV('person').property('name', 'Kelly').next()
jim = g.addV('person').property('name', 'Jim').next()
paras = g.addV('person').property('name', 'Paras').next()
denise = g.addV('person').property('name', 'Denise').next()

g.addE('is_friends_with').from(dave).to(jim).
        addE('is_friends_with').from(dave).to(kelly).
        addE('is_friends_with').from(kelly).to(jim).
        addE('is_friends_with').from(kelly).to(denise).
        addE('is_friends_with').from(jim).to(denise).
        addE('is_friends_with').from(jim).to(paras).
        addE('is_friends_with').from(paras).to(denise).iterate()

