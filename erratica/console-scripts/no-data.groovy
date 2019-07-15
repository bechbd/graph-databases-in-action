:remote connect tinkerpop.server conf/remote.yaml session
:remote console

g.V().drop().iterate()
