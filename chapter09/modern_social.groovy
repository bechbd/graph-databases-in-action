gremlin> modern = TinkerFactory.createModern()
  ==>tinkergraph[vertices:6 edges:6]
gremlin> m = modern.traversal()
  ==>graphtraversalsource[tinkergraph[vertices:6 edges:6], standard]
gremlin> m.V().valueMap(true)
  ==>{id=1, label=person, name=[marko], age=[29]}
  ==>{id=2, label=person, name=[vadas], age=[27]}
  ==>{id=3, label=software, name=[lop], lang=[java]}
  ==>{id=4, label=person, name=[josh], age=[32]}
  ==>{id=5, label=software, name=[ripple], lang=[java]}
  ==>{id=6, label=person, name=[peter], age=[35]}
gremlin> m.V().bothE('knows')
  ==>e[7][1-knows->2]
  ==>e[8][1-knows->4]
  ==>e[7][1-knows->2]
  ==>e[8][1-knows->4]
gremlin> social = m.V().bothE('knows').subgraph('soc').cap('soc').next()
  ==>tinkergraph[vertices:3 edges:2]
gremlin> soc = social.traversal()
  ==>graphtraversalsource[tinkergraph[vertices:3 edges:2], standard]
gremlin> soc.V().valueMap(true)
  ==>{id=1, label=person, name=[marko], age=[29]}
  ==>{id=2, label=person, name=[vadas], age=[27]}
  ==>{id=4, label=person, name=[josh], age=[32]}
gremlin> m.V().valueMap(true)
  ==>{id=1, label=person, name=[marko], age=[29]}
  ==>{id=2, label=person, name=[vadas], age=[27]}
  ==>{id=3, label=software, name=[lop], lang=[java]}
  ==>{id=4, label=person, name=[josh], age=[32]}
  ==>{id=5, label=software, name=[ripple], lang=[java]}
  ==>{id=6, label=person, name=[peter], age=[35]}
gremlin> m.V().has('person','first_name','josh').property('new',true)
  ==>v[4]
gremlin> m.V().valueMap(true)
  ==>{id=1, label=person, name=[marko], age=[29]}
  ==>{id=2, label=person, name=[vadas], age=[27]}
  ==>{id=3, label=software, name=[lop], lang=[java]}
  ==>{id=4, label=person, new=[true], name=[josh], age=[32]}
  ==>{id=5, label=software, name=[ripple], lang=[java]}
  ==>{id=6, label=person, name=[peter], age=[35]}
gremlin> soc.V().valueMap(true)
  ==>{id=1, label=person, name=[marko], age=[29]}
  ==>{id=2, label=person, name=[vadas], age=[27]}
  ==>{id=4, label=person, name=[josh], age=[32]}

