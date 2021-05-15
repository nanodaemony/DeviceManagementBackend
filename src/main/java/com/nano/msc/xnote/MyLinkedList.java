package com.nano.msc.xnote;

/**
 * Description:
 * Usage:
 * 1.
 *
 * @version: 1.0
 * @author: nano
 * @date: 2021/5/14 21:16
 */

public class MyLinkedList {

    class Node {//结点类
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }

    }

    Node head = new Node(0);

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {

    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {//空指针
        Node temp = head;
        while (temp.next != null) {
            if (index != 0) {
                index--;
                temp = temp.next;
                // 这里删去不必要的判断
            } else {
                return temp.next.val;
            }
        }
        return -1;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node newNode = new Node(val);
        newNode.next = head.next;
        head.next = newNode;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node newNode = new Node(val);
        Node temp = head;
        // 只写一个条件即可
        while (temp.next != null) {
            temp = temp.next;
        }
//        while (temp != null && temp.next != null) {
//            temp = temp.next;
//        }
        temp.next = newNode;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        Node newNode = new Node(val);
        if (index <= 0) {
            newNode.next = head.next;
            head.next = newNode;
        } else {
            Node temp = head;
            while (temp != null) {
                if (index != 0) {
                    index--;
                    temp = temp.next;
                    // 去掉不必要条件判断
                } else {
                    newNode.next = temp.next;
                    temp.next = newNode;
                    break;
                }
            }
        }
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0) {
            // 无需抛异常,返回即可
            return;
        } else {
            Node temp = head;
            while (temp.next != null) {
                if (index != 0) {
                    index--;
                    temp = temp.next;
                } else {
                    temp.next = temp.next.next;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
        linkedList.addAtHead(1);
        linkedList.addAtTail(3);
        Node temp = linkedList.head.next;
        while (temp != null) {
            System.out.println(temp.val + ",");
            temp = temp.next;
        }
        linkedList.addAtIndex(1, 2);   //链表变为1-> 2-> 3
        temp = linkedList.head.next;
        while (temp != null) {
            System.out.println(temp.val + ",");
            temp = temp.next;
        }
        System.out.println();
        linkedList.get(1);            //返回2
        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
        temp = linkedList.head.next;
        while (temp != null) {
            System.out.println(temp.val + ",");
            temp = temp.next;
        }
        System.out.println(linkedList.get(1));


    }
}
