:remote connect tinkerpop.server conf/remote.yaml session
:remote console

g.V().drop().iterate()

full_path_and_filename = "/path/to/simple_social_network.json"

g.io(full_path_and_filename).
  with(IO.reader,IO.graphson).
  read().
  iterate()
