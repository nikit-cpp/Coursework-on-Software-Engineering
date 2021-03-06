package gui.listeners;

import gui.WrappedTableItem;

import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

//this is the ListenerFactory implementation

public class SortListenerFactory implements Listener
{
 private Comparator currentComparator = null;
 
 private Collator col = Collator.getInstance(Locale.getDefault());
 
 public static final int INT_COMPARATOR    = 0;
 public static final int STRING_COMPARATOR = 1;
 public static final int DATE_COMPARATOR   = 2;
 public static final int DOUBLE_COMPARATOR = 3;
 public static final int HOUR_COMPARATOR   = 4;
 
 private SortListenerFactory(int _comp)
 {
     switch (_comp) 
     {
     case INT_COMPARATOR:
         currentComparator = intComparator;
         break;

     case STRING_COMPARATOR:
         currentComparator = strComparator;
         break;

     case DATE_COMPARATOR:
         currentComparator = dateComparator;
         break;
         
     case DOUBLE_COMPARATOR:
         currentComparator = doubleComparator;
         break;
         
     case HOUR_COMPARATOR:
         currentComparator = hourComparator;
         break;

     default:
         currentComparator = strComparator;
     }
 }
 
 public static Listener getListener(int _comp)
 {
     return new SortListenerFactory(_comp);
 }
 
 private int colIndex = 0;
 private int updown   = 1;
       
 // Integer Comparator
 private Comparator intComparator = new Comparator()
 {
     public int compare(Object arg0, Object arg1) {

         TableItem t1 = (TableItem)arg0;
         TableItem t2 = (TableItem)arg1;
         
         int v1 = Integer.parseInt(t1.getText(colIndex));
         int v2 = Integer.parseInt(t2.getText(colIndex));

         if (v1<v2) return 1*updown;
         if (v1>v2) return -1*updown;

         return 0;
     }    
 };
      
 // String Comparator
 private Comparator strComparator = new Comparator()
 {
     public int compare(Object arg0, Object arg1) {

         TableItem t1 = (TableItem)arg0;
         TableItem t2 = (TableItem)arg1;
         
         String v1 = (t1.getText(colIndex));
         String v2 = (t2.getText(colIndex));

         return (col.compare(v1,v2))*updown;
     }    
 };
 
 // Double Comparator
 private Comparator doubleComparator = new Comparator()
 {
     public int compare(Object arg0, Object arg1) {
         
         TableItem t1 = (TableItem)arg0;
         TableItem t2 = (TableItem)arg1;
         
         String s1 = t1.getText(colIndex);
         String s2 = t2.getText(colIndex);
         
         if(s1.isEmpty() && s2.isEmpty())
        	 return 0;
         else if(s1.isEmpty())
        	 return 1*updown;
         else if(s2.isEmpty())
        	 return -1*updown;
         
         double v1 = Double.parseDouble(s1);
         double v2 = Double.parseDouble(s2);
         
         if (v1<v2) return 1*updown;
         if (v1>v2) return -1*updown;
         
         return 0;
     }    
 };
 
 // Hour Comparator (hh:mm:ss)
 private Comparator hourComparator = new Comparator()
 {
     public int compare(Object arg0, Object arg1) {
         
         TableItem t1 = (TableItem)arg0;
         TableItem t2 = (TableItem)arg1;
         
         String v1 = (t1.getText(colIndex)).trim();
         String v2 = (t2.getText(colIndex)).trim();
         
         DateFormat df = new SimpleDateFormat("hh:mm:ss");
        
         Date d1 = null; Date d2 = null;
         
         try {                
             d1 = df.parse(v1);
         } catch (ParseException e) 
         { 
             System.out.println("[WARNING] v1 " + v1);
             try { d1 = df.parse("01:01:01"); } catch (ParseException e1) {}
         }
         
         try {               
             d2 = df.parse(v2);
         } catch (ParseException e) 
         { 
             System.out.println("[WARNING] v2 " + v2);
             try { d2 = df.parse("01:01:01"); } catch (ParseException e1) {}
         }

         if (d1.equals(d2))
             return 0;

         return updown*(d1.before(d2) ? 1 : -1);
     }    
 };
 
 private Comparator dateComparator = new Comparator()
 {
     public int compare(Object arg0, Object arg1) 
     {    
         TableItem t1 = (TableItem)arg0;
         TableItem t2 = (TableItem)arg1;
                  
         String v1 = (t1.getText(colIndex)).trim();
         String v2 = (t2.getText(colIndex)).trim();

         v1.replaceAll("-", "/");
         v2.replaceAll("-", "/");
         
         DateFormat df_europe = new SimpleDateFormat("dd/MM/yyyy");
         DateFormat df_usa = new SimpleDateFormat("yyyy/MM/dd");

         DateFormat df = df_europe;
         
         Date d1 = null; Date d2 = null;
         
         try {
             d1 = df.parse(v1);
         } catch (ParseException e) 
         { 
            //System.out.println("[WARNING] v1 " + v1);
             d1 = new Date("01/01/1900");
         }
         
         try {               
             d2 = df.parse(v2);
         } catch (ParseException e) 
         { 
             d2 = new Date("01/01/1900");
         }

         if (d1.equals(d2))
             return 0;

         return updown*(d1.before(d2) ? 1 : -1);
     }    
 };
     
 public void handleEvent(Event e) {
     
     updown = (updown == 1 ? -1 : 1);
     
     TableColumn currentColumn = (TableColumn)e.widget;
     Table table = currentColumn.getParent();
 
     colIndex = searchColumnIndex(currentColumn);
     
     table.setRedraw(false);
     
     TableItem[] items = table.getItems();
     
     // TODO подумать и сделать лучше
     try{
    	 Arrays.sort(items,currentComparator);
     }catch(NumberFormatException ex){
    	 ex.printStackTrace();
     }
     
     table.setItemCount(items.length);
     
     for (int i = 0;i<items.length;i++)
     {   
    	 if(items[i] instanceof WrappedTableItem){
	    	 WrappedTableItem item = new WrappedTableItem(table,SWT.NONE,i);
	         WrappedTableItem oldWrappedItem = (WrappedTableItem) items[i];
	         item.setText(getData(items[i]));
	         item.setChecked(items[i].getChecked());
	         item.arrListPos=oldWrappedItem.arrListPos;
	         items[i].dispose();
    	 }else{
    		 TableItem item = new TableItem(table,SWT.NONE,i);
             item.setText(getData(items[i]));
             item.setChecked(items[i].getChecked());
             items[i].dispose();
    	 }
     }
          
     table.setRedraw(true);     
 }
 
 private String[] getData(TableItem t)
 {
     Table table = t.getParent();
     
     int colCount = table.getColumnCount();
     String [] s = new String[colCount];
     
     for (int i = 0;i<colCount;i++)
         s[i] = t.getText(i);
             
     return s;
     
 }
 
 private int searchColumnIndex(TableColumn currentColumn)
 {
     Table table = currentColumn.getParent();
     
     int in = 0;
     
     for (int i = 0;i<table.getColumnCount();i++)
         if (table.getColumn(i) == currentColumn)
             in = i;
     
     return in;
 }


}