// Example 1: different vertex label for each contact type
g.V().drop().iterate()

g.addV('person').property('first_name','Ted').as('ted').
  addV('email').property('address','fake@fake.com').as('email').
    addE('has_email').from('ted').to('email').
  addV('phone').property('number','555-1212').as('phone').
    addE('has_phone').from('ted').to('phone').
  addV('fax').property('number','555-1213').as('fax').
    addE('has_fax').from('ted').to('fax').
  iterate();

g.V().groupCount().by(label)

g.V().has('person', 'first_name', 'Ted').
        out('has_phone').
        values('number')

g.V().has('person', 'first_name', 'Ted').
  union(
    out('has_phone').values('number'),
    out('has_email').values('address'),
    out('has_fax').values('number')
  )

g.V().
  union(
    out('has_phone').values('number'),
    out('has_email').values('address'),
    out('has_fax').values('number' )
  )


// Example 2: single "contact" vertex label
g.V().drop().iterate()

g.addV('person').property('first_name','Ted').as('ted').
  addV('contact').
    property('type','email').
    property('address','fake@fake.com').as('email').
    addE('has_email').from('ted').to('email').
   addV('contact').
    property('type','phone').
    property('number','555-1212').as('phone').
    addE('has_phone').from('ted').to('phone').
  addV('contact').
    property('type','fax').
    property('number','555-1213').as('fax').
    addE('has_fax').from('ted').to('fax').
  iterate();

g.V().groupCount().by(label)

g.V().has('person', 'first_name', 'Ted').
  out('has_phone').
  values('number')

g.V().has('person', 'first_name', 'Ted').
  union(
    out('has_phone').values('number'),
    out('has_email').values('address'),
    out('has_fax').values('number')
  )

g.V().
  hasLabel('contact').
  values('number', 'address')


// Example 3: single "has_a" edge label
g.V().drop().iterate()

g.addV('person').property('first_name','Ted').as('ted').
    addV('contact').
      property('type','email').
      property('address','fake@fake.com').as('email').
      addE('has_a').from('ted').to('email').
    addV('contact').
      property('type','phone').
      property('number','555-1212').as('phone').
      addE('has_a').from('ted').to('phone').
    addV('contact').
      property('type','fax').
      property('number','555-1213').as('fax').
      addE('has_a').from('ted').to('fax').
    iterate();

g.V().groupCount().by(label)

g.V().has('person', 'first_name', 'Ted').
  out('has_a').
  has('contact', 'type', 'phone').
  values('number')

g.V().has('person', 'first_name', 'Ted').
  out('has_a').
  values('number', 'address')

g.V().hasLabel('contact').
  values('number', 'address')


g.V().hasLabel('contact').
  valueMap('number', 'address')

