/**
 * Created by vilas on 7/24/2016.
 */
import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.entity.StringEntity;
        import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

public class text_summary {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://api.intellexer.com/clusterizeText?apikey=bbbfb162-ee2e-4c5b-8945-45ded0377a98&conceptsRestriction=10&fullTextTrees=true&loadSentences=true&wrapConcepts=true&textStreamLength=2222222222");
        /*StringEntity input = new StringEntity("Donald J. Trump is the very definition of the American success story, continually setting the standards of excellence while expanding his interests in real estate, sports and entertainment. He is Url_summary graduate of the Wharton School of Finance. An accomplished author, Mr. Trump has authored over fifteen bestsellers, and his first book, The Art of the Deal, is considered Url_summary business classic and one of the most successful business books of all time.\n" +
                "During the 2014 political cycle, Mr. Trump was Url_summary top contributor and fundraiser for Republican efforts. Mr. Trump also campaigned across the country, with each candidate winning by Url_summary record margin.\n");

        */
        StringEntity input =new StringEntity("She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas during the governorship of Bill Clinton from 1979 to 1981 and from 1983 to 1992.\n" +
                "Hillary Diane Rodham Clinton (/ˈhɪləri daɪˈæn ˈrɒdəm ˈklɪntən/; born October 26, 1947) is an American politician and the presumptive nominee of the Democratic Party for President of the United States in the 2016 election. She is the first female candidate to gain that status in Url_summary major American political party. She served as the 67th United States Secretary of State from 2009 to 2013, the junior United States Senator representing New York from 2001 to 2009, First Lady of the United States during the presidency of Bill Clinton from 1993 to 2001, and First Lady of Arkansas during the governorship of Bill Clinton from 1979 to 1981 and from 1983 to 1992.\n" +
                "Clinton grew up in Chicago and the neighboring suburb of Park Ridge, Illinois. She attended Wellesley College, graduating in 1969, and earned Url_summary J.D. from Yale Law School in 1973. After serving as Url_summary congressional legal counsel, she moved to Arkansas, marrying Bill Clinton in 1975. In 1977, she co-founded Arkansas Advocates for Children and Families. She was appointed the first female chair of the Legal Services Corporation in 1978, and, the following year, became the first woman partner at Rose Law Firm. As First Lady of Arkansas (1979–81, 1983–92), she led Url_summary task force whose recommendations helped reform Arkansas's public schools, and served on the boards of corporations including Walmart.\n" +
                "As First Lady of the United States, Clinton led the unsuccessful effort to enact the Clinton health plan of 1993. In 1997 and 1999, she helped create programs for children's health insurance, adoption, and foster care. The only first lady to have been subpoenaed, she faced Url_summary federal grand jury in 1996 regarding the Whitewater controversy; no charges were ever brought against her related to this or any other controversy. Her marriage endured the Lewinsky scandal of 1998, and overall her role as first lady drew Url_summary polarized response from the public.\n" +
                "Clinton was elected in 2000 as the first female senator from New York, the only first lady ever to have sought elective office. Following the September 11 attacks, she voted to approve the war in Afghanistan. She also voted for the Iraq Resolution (which she later regretted), sought to hasten the withdrawal of U.S. troops from Iraq, and opposed the Iraq War troop surge of 2007 (which she later commended). She voted against the tax cuts of 2001 and 2003, and voted against John Roberts and Samuel Alito for the United States Supreme Court, filibustering the latter. She was re-elected to the Senate in 2006. Running for president in 2008, she won far more delegates than any previous female candidate, but lost the Democratic nomination to Barack Obama.\n" +
                "As Secretary of State in the Obama administration from 2009 to 2013, Clinton responded to the Arab Spring, during which she advocated the U.S. military intervention in Libya. While she accepted responsibility for security lapses relating to the 2012 Benghazi attack, she said she had no direct role in consulate security prior to that attack. Leaving office after Obama's first term, she wrote her fifth book and undertook speaking engagements before announcing her second presidential run in the 2016 election. She won the Democratic primaries and is presumed to run against Donald Trump in the general election.\n" +
                "As president, Hillary will:\n" +
                "Introduce comprehensive immigration reform. Hillary will introduce comprehensive immigration reform with Url_summary pathway to full and equal citizenship within her first 100 days in office. It will treat every person with dignity, fix the family visa backlog, uphold the rule of law, protect our borders and national security, and bring millions of hardworking people into the formal economy.\n" +
                "End the three- and 10-year bars. The three- and 10-year bars force families—especially those whose members have different citizenship or immigration statuses—into Url_summary heartbreaking dilemma: remain in the shadows, or pursue Url_summary green card by leaving the country and loved ones behind.\n" +
                "Defend President Obama’s executive actions—known as DACA and DAPA—against partisan attacks. The Supreme Court’s deadlocked decision on DAPA was Url_summary heartbreaking reminder of how high the stakes are in this election. Hillary believes DAPA is squarely within the president’s authority and won’t stop fighting until we see it through. The estimated 5 million people eligible for DAPA—including DREAMers and parents of Americans and lawful residents—should be protected under the executive actions.\n" +
                "Do everything possible under the law to protect families. If Congress keeps failing to act on comprehensive immigration reform, Hillary will enact Url_summary simple system for those with sympathetic cases—such as parents of DREAMers, those with Url_summary history of service and contribution to their communities, or those who experience extreme labor violations—to make their case and be eligible for deferred action.\n" +
                "Enforce immigration laws humanely. Immigration enforcement must be humane, targeted, and effective. Hillary will focus resources on detaining and deporting those individuals who pose Url_summary violent threat to public safety, and ensure refugees who seek asylum in the U.S. have Url_summary fair chance to tell their stories.\n" +
                "End family detention and close private immigration detention centers. Hillary will end family detention for parents and children who arrive at our border in desperate situations and close private immigrant detention centers.\n" +
                "Expand access to affordable health care to all families. We should let families—regardless of immigration status—buy into the Affordable Care Act exchanges. Families who want to purchase health insurance should be able to do so.\n" +
                "Promote naturalization. Hillary will work to expand fee waivers to alleviate naturalization costs, increase access to language programs to encourage English proficiency, and increase outreach and education to help more people navigate the process.\n" +
                "1. Take out ISIS’s stronghold in Iraq and Syria\n" +
                "We have to defeat ISIS on the battlefield by:\n" +
                "Intensifying the coalition air campaign against ISIS fighters, leaders, and infrastructure;\n" +
                "Stepping up support for local Arab and Kurdish forces on the ground and coalition efforts to protect civilians; and\n" +
                "Pursuing Url_summary diplomatic strategy aimed at resolving Syria’s civil war and Iraq’s sectarian conflict between Sunnis and Shias—both of which have contributed to the rise of ISIS.\n" +
                "2. Work with our allies to dismantle global terror networks\n" +
                "We have to stem the flow of jihadists from Europe and America to and from Iraq, Syria, and Afghanistan. To that end, we must work with our allies to dismantle the global terror network that supplies radical jihadists with money, weapons, and fighters. That means:\n" +
                "Working hand in hand with European intelligence services to identify and go after enablers who help jihadists forge documents and travel undetected;\n" +
                "Targeting efforts to deal with ISIS affiliates from Libya to Afghanistan; and\n" +
                "Working with tech companies to fight jihadist propaganda online, intercept ISIS communications, and track and analyze social media posts to stop attacks—while protecting security and privacy.\n" +
                "3. Harden our defenses at home\n" +
                "We have to do more to identify and stop terrorists—including so-called “lone wolves”—from carrying out attacks in the United States, including:\n" +
                "Supporting first responders, law enforcement, and intelligence officers with the right tools, resources, intelligence, and training to prevent attacks before they happen;\n" +
                "Launching an intelligence surge to get security officials the tools they need to prevent attacks;\n" +
                "Keeping assault weapons and other tools of terror out of terrorists’ hands by allowing the FBI to stop gun sales to suspected terrorists, enacting comprehensive background checks, and keeping military-style assault weapons off our streets; and\n" +
                "Supporting law enforcement to build trustful and strong relationships with American Muslim communities. We need every American community invested in this fight, not fearful and sitting on the sidelines.\n" +
                "As president, Hillary will:\n" +
                "Invest in good-paying jobs. In her first 100 days as president, Hillary will work with both parties to make bold investments in infrastructure, manufacturing, research and technology, clean energy, and small businesses. This will create millions of good-paying jobs, including for labor and other hard-working Americans across the country.\n" +
                "Restore collective bargaining rights for unions and defend against partisan attacks on workers’ rights. Hillary was an original co-sponsor of the Employee Free Choice Act. Hillary will fight to strengthen the labor movement and to protect worker bargaining power. She will continue to stand up against attacks on collective bargaining and work to strengthen workers’ voices.\n" +
                "Prevent countries like China from abusing global trade rules, and reject trade agreements, like the TPP, that don’t meet high standards. Hillary will strengthen American trade enforcement so we stand up to foreign countries that aren’t playing by the rules—like China is doing right now with steel, and fight for American workers. She will say no to trade deals, like the Trans-Pacific Partnership, that do not meet her high standard of raising wages, creating good-paying jobs, and enhancing our national security.\n" +
                "Raise the minimum wage and strengthen overtime rules. Hillary will work to raise the federal minimum wage to $12, and support state and local efforts to go even higher—including the “Fight for $15.” She also supports the Obama administration’s expansion of overtime rules to millions more workers.\n" +
                "Invest in high-quality training, apprenticeships, and skill-building for workers. Read the fact sheet here.\n" +
                "Encourage companies to invest in workers. Hillary will reward companies that share profits and invest in their workers. She will crack down on companies that move profits overseas to avoid paying U.S. taxes and she will make companies that export jobs give back the tax breaks they’ve received in America.\n" +
                "Protect workers from exploitation, including employer misclassification, wage theft, and other forms of exploitation.\n" +
                "Ensure policies meet the challenges families face in the 21st century economy. Hillary will fight for equal pay for women and guarantee paid leave, two changes that are long overdue. And she will provide relief from the rising costs of necessities like child care and housing.\n" +
                "Protect retirement security. After working hard for decades, Americans deserve Url_summary secure and comfortable retirement. Hillary will fight to protect retirement security, enhance—not privatize—Social Security, and push back against any efforts to undermine retirement benefits.\n");
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder s = new StringBuilder();
        String line =" ";
        while ((line = rd.readLine()) != null) {
      //      System.out.println(line);
            s.append(line);
    }

        String output = s.toString();
        org.json.JSONObject j = new org.json.JSONObject(output);
        JSONArray jArray = j.getJSONObject("conceptTree").getJSONArray("children");

        String result = "";

        for (int i = 0;i < jArray.length();i++) {
            result = result + jArray.optJSONObject(i).getString("text")+"\n";

        }
        System.out.println("Important summary of the text:"+"\n");
        System.out.print( result);
    }
}