package ke.wealthdiscipline.app;

import android.app.*;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.*;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import org.json.*;
import java.text.NumberFormat;
import java.util.*;

public class MainActivity extends Activity {
  LinearLayout root, content, list;
  EditText salaryInput, descInput, amountInput;
  TextView summary;
  JSONArray expenses;
  int teal=Color.rgb(15,118,110), ink=Color.rgb(23,43,54), muted=Color.rgb(105,125,132);
  NumberFormat nf=NumberFormat.getNumberInstance(Locale.US);
  @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(Color.rgb(16,47,51));getWindow().setNavigationBarColor(Color.rgb(16,47,51));load();render();}
  void load(){SharedPreferences p=getSharedPreferences("wealth",MODE_PRIVATE);String raw=p.getString("expenses",null);try{if(raw==null){expenses=new JSONArray();JSONObject rent=new JSONObject();rent.put("description","Rent");rent.put("amount",3500);rent.put("date",new java.text.SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(new Date()));expenses.put(rent);JSONObject wifi=new JSONObject();wifi.put("description","Wi-Fi");wifi.put("amount",2000);wifi.put("date",new java.text.SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(new Date()));expenses.put(wifi);p.edit().putString("expenses",expenses.toString()).apply();}else{expenses=new JSONArray(raw);}}catch(Exception e){expenses=new JSONArray();} }
  void save(){getSharedPreferences("wealth",MODE_PRIVATE).edit().putString("expenses",expenses.toString()).putInt("salary",salary()).apply();}
  int salary(){SharedPreferences p=getSharedPreferences("wealth",MODE_PRIVATE);return p.getInt("salary",35000);}
  int sum(){int s=0;for(int i=0;i<expenses.length();i++)s+=expenses.optJSONObject(i).optInt("amount");return s;}
  String cash(int n){return "KSh "+nf.format(n);}
  TextView text(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD);t.setPadding(0,5,0,5);return t;}
  GradientDrawable bg(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(radius);return d;}
  LinearLayout vertical(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);return l;}
  LinearLayout horizontal(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.HORIZONTAL);l.setGravity(Gravity.CENTER_VERTICAL);return l;}
  void render(){
    root=vertical();root.setBackgroundColor(Color.rgb(244,247,247));
    ScrollView scroll=new ScrollView(this);content=vertical();content.setPadding(20,20,20,24);scroll.addView(content);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
    TextView brand=text("🇰🇪  WEALTH DISCIPLINE KENYA",14,teal,true);content.addView(brand);
    content.addView(text("Your money. Your plan.",28,ink,true));
    content.addView(text("Build steady habits, protect your future, and invest thoughtfully.",14,muted,false));
    LinearLayout cards=vertical();cards.setPadding(17,15,17,15);cards.setBackground(bg(Color.WHITE,22));cards.setElevation(2);LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.topMargin=18;content.addView(cards,cp);
    cards.addView(text("MONTHLY SALARY",12,muted,true));cards.addView(text(cash(salary()),29,teal,true));
    cards.addView(text("Recorded expenses",13,muted,false));cards.addView(text(cash(sum()),20,ink,true));
    cards.addView(text("Balance after recorded expenses",13,muted,false));cards.addView(text(cash(salary()-sum()),23,(salary()-sum()>=0?teal:Color.RED),true));
    content.addView(text("Your starting bills",19,ink,true));
    content.addView(text("Rent: KSh 3,500     •     Wi-Fi: KSh 2,000",14,muted,false));
    content.addView(text("Add expense",19,ink,true));
    descInput=new EditText(this);descInput.setSingleLine(true);descInput.setHint("Description (e.g. groceries)");content.addView(descInput,fieldParams());
    amountInput=new EditText(this);amountInput.setSingleLine(true);amountInput.setInputType(2);amountInput.setHint("Amount in KSh");content.addView(amountInput,fieldParams());
    Button add=button("＋  Add expense");content.addView(add,buttonParams());add.setOnClickListener(v->addExpense());
    LinearLayout row=horizontal();TextView history=text("Expense history",19,ink,true);row.addView(history,new LinearLayout.LayoutParams(0,-2,1));Button edit=button("Edit salary");row.addView(edit);edit.setOnClickListener(v->editSalary());content.addView(row);
    list=vertical();content.addView(list);showExpenses();
    content.addView(text("Savings & investment discipline",19,ink,true));
    content.addView(text("1. Cover essentials and expensive debt first.\n2. Build an emergency fund covering 3–6 months of essential costs.\n3. Invest only money you can leave invested, after checking risk, fees and provider authorization.\n4. Review spending every week.",14,muted,false));
    content.addView(text("This app stores records on this device. It does not connect to M-Pesa or banks, and does not make investments.",12,muted,false));
    setContentView(root);
  }
  LinearLayout.LayoutParams fieldParams(){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=6;p.bottomMargin=5;return p;}
  LinearLayout.LayoutParams buttonParams(){LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=8;p.bottomMargin=14;return p;}
  Button button(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(teal));return b;}
  void addExpense(){String d=descInput.getText().toString().trim(),a=amountInput.getText().toString().trim();if(d.isEmpty()||a.isEmpty()){Toast.makeText(this,"Enter a description and amount.",Toast.LENGTH_SHORT).show();return;}try{int amount=Integer.parseInt(a);if(amount<=0)throw new Exception();JSONObject e=new JSONObject();e.put("description",d);e.put("amount",amount);e.put("date",new java.text.SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(new Date()));expenses.put(e);save();((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(amountInput.getWindowToken(),0);render();}catch(Exception ex){Toast.makeText(this,"Enter a valid amount greater than zero.",Toast.LENGTH_SHORT).show();}}
  void showExpenses(){list.removeAllViews();if(expenses.length()==0){list.addView(text("No expenses recorded yet.",13,muted,false));return;}for(int i=expenses.length()-1;i>=0;i--){final int ix=i;JSONObject e=expenses.optJSONObject(i);LinearLayout row=horizontal();row.setPadding(12,8,4,8);row.setBackground(bg(Color.WHITE,12));LinearLayout.LayoutParams rp=new LinearLayout.LayoutParams(-1,-2);rp.topMargin=5;list.addView(row,rp);LinearLayout details=vertical();details.addView(text(e.optString("description"),14,ink,true));details.addView(text(e.optString("date"),11,muted,false));row.addView(details,new LinearLayout.LayoutParams(0,-2,1));row.addView(text(cash(e.optInt("amount")),14,teal,true));Button del=new Button(this);del.setText("×");del.setTextSize(20);del.setTextColor(Color.RED);del.setBackgroundColor(Color.TRANSPARENT);row.addView(del);del.setOnClickListener(v->{expenses.remove(ix);save();render();});}}
  void editSalary(){EditText input=new EditText(this);input.setInputType(2);input.setText(String.valueOf(salary()));new AlertDialog.Builder(this).setTitle("Monthly salary (KSh)").setView(input).setNegativeButton("Cancel",null).setPositiveButton("Save",(d,w)->{try{int s=Integer.parseInt(input.getText().toString());if(s<0)throw new Exception();getSharedPreferences("wealth",MODE_PRIVATE).edit().putInt("salary",s).apply();render();}catch(Exception ex){Toast.makeText(this,"Enter a valid salary.",Toast.LENGTH_SHORT).show();}}).show();}
}
