package touro.snake.strategy.astar;

import touro.snake.*;
import touro.snake.strategy.SnakeStrategy;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class AStarStrategy implements SnakeStrategy {
    Snake snake;
    Garden garden;
    @Override
    public void turnSnake(Snake snake, Garden garden) {
        this.snake = snake;
        this.garden = garden;
        Node startNode = new Node(snake.getHead());
        boolean isReady = false;
            while(!isReady) {
                if(garden.getFood() == null) return;
                else isReady = true;
            }
            Node endNode = new Node(garden.getFood());

        ArrayList<Node> path = createPath(startNode, endNode);
        System.out.println("path = "+ path);

    }

    public ArrayList<Node> createPath(Node startNode, Node endNode){
        //        Create start and end node
        ArrayList<Node> path = new ArrayList<>();


//     Initialize both open and closed list
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();

//     Add the start node
        openList.add(startNode);

        while(openList.size() > 0) {

//        Get the current node
            endNode = new Node(garden.getFood());
            Node currentNode = openList.get(0);
            int currentIndex = 0;
            for(int index = 0; index<openList.size(); index++) {
                Node item = openList.get(index);
                if( item.getCost() < currentNode.getCost()) {
                    currentNode = item;
                    currentIndex = index;
                }
            }

//        Pop current off open list, add to closed list
            openList.remove(currentIndex);
            closedList.add(currentNode);

//        Found the goal

            if( currentNode == endNode) {
                Node current;
                current = currentNode;
                while (current != null) {
                    path.add(current);
                    current = currentNode.getParent();

                }
                path = reverseArrayList(path);
            }
//        Generate children
            ArrayList<Node> children = new ArrayList<>();
            ArrayList<Node> neighbors = findNeighbors();

//            Get node position
            for(int index = 0; index<neighbors.size(); index++) {
                Node newNodePos = new Node(currentNode.getX() + neighbors.get(index).getX(), currentNode.getY() +  neighbors.get(index).getY());

//            Make sure within range
                if (!newNodePos.inBounds()) continue;


//            Append
                children.add(newNodePos);
            }
//        Loop through children
            for(Node child : children) {

//            Child is on the closed list

                for (Node closedChild : closedList) {
                    if (child == closedChild) {
                        continue;
                    }
                }

//            Child is already in the open list
                for (Node openNode : openList) {
                    if (child == openNode && child.distance(startNode) > openNode.distance(startNode))
                        continue;
                }
//            Add the child to the open list
                openList.add(child);
            }
        }

        return  path;
    }
    public ArrayList<Node> reverseArrayList(ArrayList<Node> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<Node> revArrayList = new ArrayList<Node>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }

    public ArrayList<Node> findNeighbors(){


        Node left = new Node(-1,-0);
        Node right = new Node(1,0);
        Node up = new Node(0,1);
        Node down = new Node(0,-1);
        Node diDownLeft = new Node(-1,-1);
        Node diDownRight = new Node(-1, 1);
        Node diUpLeft = new Node(1, -1);
        Node diUpRight = new Node(1, 1);


        ArrayList<Node> neighbors = new ArrayList<>();
        neighbors.add(left);
        neighbors.add(right);
        neighbors.add(up);
        neighbors.add(down);
        neighbors.add(diDownLeft);
        neighbors.add(diDownRight);
        neighbors.add(diUpLeft);
        neighbors.add(diUpRight);

        return neighbors;
    }
}



