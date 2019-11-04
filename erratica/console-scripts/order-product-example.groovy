:remote connect tinkerpop.server conf/remote.yaml session
:remote console

g.V().drop().iterate()

abc123 = g.addV('order').property('number','ABC123').next()
def234 = g.addV('order').property('number','DEF234').next()

widget1 = g.addV('product').property('name','widget 1').next()
widget2 = g.addV('product').property('name','widget 2').next()
widget3 = g.addV('product').property('name','widget 3').next()

g.addE('contains').from(abc123).to(widget1).
  addE('contains').from(abc123).to(widget2).
  addE('contains').from(def234).to(widget2).
  addE('contains').from(def234).to(widget3).
  iterate()


g.V().has('person', 'first_name', 'Dave').
  repeat(
    out('friends')
  ).times(2).values('first_name')


g.V().has('person', 'name', 'Dave').
  out().as('f').
  out().as('foff').values('first_name')