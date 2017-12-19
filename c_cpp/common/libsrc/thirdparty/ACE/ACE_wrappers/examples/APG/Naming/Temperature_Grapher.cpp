// Temperature_Grapher.cpp,v 1.3 2004/10/18 21:49:16 shuston Exp

#include "ace/OS_NS_unistd.h"
#include "ace/Log_Msg.h"

#include "Graph.h"
#include "Graphable_Element.h"
#include "Temperature_Grapher.h"

// Listing 1 code/ch21
void Temperature_Grapher::monitor (void)
{
  for (;;)
    {
      this->update_graph ();
      ACE_OS::sleep (this->opt_.poll_interval ());
    }
}
// Listing 1

// Listing 2 code/ch21
void Temperature_Grapher::update_graph (void)
{
  Name_Binding_Ptr lastUpdate
    (this->naming_context_.fetch ("lastUpdate"));

  if (!lastUpdate.get ())
    {
      ACE_DEBUG ((LM_DEBUG, ACE_TEXT ("No data to graph\n")));
      return;
    }
  // Listing 2

  // Listing 3 code/ch21
  Name_Binding_Ptr lastGraphed
    (this->naming_context_.fetch ("lastGraphed"));

  if (lastGraphed.get () &&
      lastGraphed->int_value () == lastUpdate->int_value ())
    {
      ACE_DEBUG ((LM_DEBUG, ACE_TEXT ("Data already graphed\n")));
      return;
    }
  // Listing 3

  // Listing 4 code/ch21
  ACE_BINDING_SET set;
  if (this->naming_context_.list_name_entries
        (set, "history[") != 0)
    {
      ACE_DEBUG ((LM_INFO,
                  ACE_TEXT ("There's nothing to graph\n")));
      return;
    }
  // Listing 4

#if !defined (ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION)
  // Listing 5 code/ch21
  Graphable_Element_List graphable;
  ACE_BINDING_ITERATOR set_iterator (set);
  for (ACE_Name_Binding *entry = 0;
       set_iterator.next (entry) != 0;
       set_iterator.advance ())
    {
      Name_Binding binding (entry);
      ACE_DEBUG ((LM_DEBUG, ACE_TEXT ("%s\t%s\t%s\n"),
                  binding.type (),
                  binding.name (),
                  binding.value ()));

      Graphable_Element *ge = new Graphable_Element (entry);
      graphable.push_back (*ge);
    }
  // Listing 5

  // Listing 6 code/ch21
  Graph g;
  g.graph (lastUpdate->value (), graphable);
  this->naming_context_.rebind ("lastGraphed",
                                lastUpdate->int_value ());
  // Listing 6
#endif /* ACE_HAS_EXPLICIT_TEMPLATE_INSTANTIATION */
}
