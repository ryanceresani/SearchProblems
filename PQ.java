import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Priority queue of SearchNodes that supports lowering priority value in logarithmic time.
 * 
 * @author Vincent Cicirello
 * @version CSIS4463
 */
public class PQ<E extends SearchNode> extends AbstractQueue<E> {

	private ArrayList<E> heap;
	private HashMap<State,Integer> index;
	private Comparator<? super E> comparator;
	
	/**
	 * Constructor must be supplied with a Comparator to enable comparing
	 * which of a pair of queue elements has lower priority value.
	 * 
	 * @param comparator The Comparator 
	 */
	public PQ(Comparator<? super E> comparator) {
		heap = new ArrayList<E>();
		index = new HashMap<State,Integer>();
		this.comparator = comparator;
	}
	
	/**
	 * Adds a SearchNode to the priority queue provided either: (a) the PQ doesn't contain
	 * another SearchNode containing the same State object, or (b) the PQ does contain a
	 * SearchNode containing the same State object, but the new one has lower priority value.
	 * In the latter case, the existing SearchNode is replaced by the new one, and PQ is restructured 
	 * accordingly.
	 * 
	 * @param e The SearchNode
	 * @return true if the new SearchNode was added, or false if it was not added due to existing SearchNode
	 * containing same State but with lower priority value.
	 */
	@Override
	public boolean offer(E e) {
		
		int i = heap.size();
		if (index.containsKey(e.getState())) {
			i = index.get(e.getState());
			if (comparator.compare(e, heap.get(i))>0) {
				return false;
			}
			heap.set(i, e);
		} else {
			heap.add(e);
		}
		while (i > 0 && comparator.compare(heap.get((i-1)/2), heap.get(i))>0) {
			E parent = heap.get((i-1)/2);
			heap.set((i-1)/2, e);
			heap.set(i, parent);
			index.put(parent.getState(),i);
			i = (i-1)/2;
		}
		index.put(e.getState(),i);
		
		return true;
	}

	/**
	 * Accesses the top of the PQ.  Does not change the PQ
	 * @return The SearchNode with lowest priority value.  Returns null if empty.
	 */
	@Override
	public E peek() {
		return (heap.size()>0) ? heap.get(0) : null;
	}

	/**
	 * Accesses and removes the top of the PQ.
	 * @return The SearchNode with lowest priority value.  Returns null if empty.
	 */
	@Override
	public E poll() {
		if (heap.size()==0) return null;
		E top = heap.get(0);
		index.remove(top.getState());
		
		if (heap.size()>1) {
			heap.set(0,heap.remove(heap.size()-1));
			index.put(heap.get(0).getState(), 0);
			int p = 0;
			while (2*p+1 < heap.size()) {
				int c = 2*p+1;
				if (c+1 < heap.size() && comparator.compare(heap.get(c), heap.get(c+1))>0) {
					c = c+1;
				}
				if (comparator.compare(heap.get(p), heap.get(c))>0) {
					E child = heap.get(c);
					E parent = heap.get(p);
					heap.set(c, parent);
					heap.set(p, child);
					index.put(parent.getState(), c);
					index.put(child.getState(), p);
				} else {
					break;
				}
				p = c;
			}
		} else {
			heap.remove(0);
		}
		
		return top;
	}

	/**
	 * Returns an iterator for the PQ.  The elements are iterated over in an arbitrary order.
	 */
	@Override
	public Iterator<E> iterator() {
		return heap.iterator();
	}

	/**
	 * Gets the number of elements in this PQ.
	 * @return number of elements in the PQ.
	 */
	@Override
	public int size() {
		return heap.size();
	}

}
