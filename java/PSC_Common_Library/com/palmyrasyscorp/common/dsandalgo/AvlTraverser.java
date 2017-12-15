/**
 * 
 */
package com.palmyrasyscorp.common.dsandalgo;

/**
 * @author Prem
 *
 */
public class AvlTraverser {

	private AvlTree m_avlTree = null;
	private AvlNode m_avlNode = null;
	private short m_avlHeight = 0;
	// private long m_avlGeneration = 0;
	private AvlNode[] m_avlStack = new AvlNode[AvlTree.MAX_HEIGHT];
	
	/**
	 * 
	 * @param avlTree
	 */
	public AvlTraverser(AvlTree avlTree) {
		m_avlTree = avlTree;
	}
	
	/**
	 * 
	 */
	protected void finalize() throws Throwable {
		for (int i=0; i<m_avlStack.length; i++) {
			m_avlStack[i] = null;
		}
		m_avlStack = null;
		m_avlTree = null;
		m_avlNode = null;
		super.finalize();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Comparable getFirst() {
		Comparable ret = null;
		
		AvlNode x = m_avlTree.root;
		
		if (x != null) {
			while (x.left != null) {
				if (m_avlHeight >= AvlTree.MAX_HEIGHT) {
					System.out.println("Avl tree heigh exceeded!!!Exitting...");
					System.exit(-1);
				}
				m_avlStack[m_avlHeight++] = x;
				x = x.left;
			}
		}
		
		m_avlNode = x;
		
		ret = (x != null ? x.element : null);
		
		return (ret);
	}
	
	/**
	 * 
	 * @return
	 * null - if there are no more data items
	 */
	public Comparable getNext() {
		Comparable ret = null;
		
		AvlNode x = m_avlNode;
		if (x == null) {
			
		} else if (x.right != null) {
			m_avlStack[m_avlHeight++] = x;
			x = x.right;
			while (x.left != null) {
				m_avlStack[m_avlHeight++] = x;
				x = x.left;
			}
		} else {
			AvlNode y;
			do {
				if (m_avlHeight == 0) {
					m_avlNode = null;
					return (ret);
				}
				y = x;
				x = m_avlStack[--m_avlHeight];
			} while (y.element.compareTo(x.right.element)==0);
		}
		
		m_avlNode = x;
		
		ret = x.element;
		
		return (ret);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		AvlTree tr = new AvlTree();
		AvlTraverser trav = new AvlTraverser(tr);
		for (int i=1; i<=100; i++) {
			Integer I = new Integer(i);
			tr.insert(I);
		}
		for (Integer I = (Integer)trav.getFirst(); I != null;
		     I = (Integer)trav.getNext()) {
			System.out.println(I);
		}
	}
}
