package normalizer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import options.OptId;
import options.Options;

import com.atlascopco.hunspell.Hunspell;

public class Normalizer {
	private Options options;
	private ArrayList<Hunspell> normalizeDicts;
		
	public Normalizer() {
		this.options = Options.getInstance();
		final String dicPath = new File(options.getString(OptId.DIC_PATH)).getAbsolutePath();
		final String affPath = new File(options.getString(OptId.AFF_PATH)).getAbsolutePath();
		
		normalizeDicts = new ArrayList<Hunspell>();
		normalizeDicts.add(new Hunspell(dicPath, affPath));
	}

	/**
	 * Возвращает список корней(стемов) для данного слова
	 * @param t
	 * @return
	 */
	public List<String> normalize(String word){
		List<String> out=new ArrayList<String>();
		
		for(Hunspell hunspell : normalizeDicts){
			word = UTF8_SystemDefault(word);
			List<String> stemList = hunspell.stem(word);
						
			for(String st : stemList){
				if(!out.contains(st)){
					out.add(SystemDefault_UTF8(st));
				}
			}
		}
		
		return out;
	}
	
	/**
	 * Костыль для зависимости hunspell от умолчальной кодировки 1251 под виндой
	 */
	public static String UTF8_SystemDefault(String in){
		if(Charset.defaultCharset().equals(Charset.forName("UTF-8")))
			return in;
		return new String(in.getBytes(Charset.forName("UTF-8")), Charset.defaultCharset());
	}
	
	/**
	 * Костыль для зависимости hunspell от умолчальной кодировки 1251 под виндой
	 */
	public static String SystemDefault_UTF8(String in){
		if(Charset.defaultCharset().equals(Charset.forName("UTF-8")))
			return in;
		return new String(in.getBytes(Charset.defaultCharset()), Charset.forName("UTF-8"));
	}
}
