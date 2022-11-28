package io.crtp.ec135;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.crtp.ec135.utilities.EC135Properties;
import io.crtp.ec135.utilities.Constants;

import org.bitcoinj.utils.BlockFileLoader;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.Script.ScriptType;

import java.security.MessageDigest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

import java.io.File;
import java.util.ArrayList;  
import java.util.LinkedList;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.HashSet;
import java.util.Arrays;

import java.text.SimpleDateFormat;

import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;

public class DatReader {

    private static final Logger log = LoggerFactory.getLogger(DatReader.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    static MainNetParams params = MainNetParams.get();

    EC135Properties ec135props = EC135Properties.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
    List<Transaction> trxs = null;
    Transaction trx = null;
    List<TransactionOutput> trxOutputs = null;
    NetworkParameters np = null;
    String PREFIX = null;
    HashSet<String> addrSet = new HashSet();

    int address_id = 1;

    public DatReader() {
        log.debug("constructor");
        PREFIX = ec135props.getProp( Constants.dat_directory );
    }
    
    public void mone() {

        log.debug( "mone ec135v02" );
        log.debug( PREFIX );

        List<File> list = new LinkedList<File>();

        File folder = new File(PREFIX);
        File[] listOfFiles = folder.listFiles();
        
        Arrays.sort(listOfFiles, NameFileComparator.NAME_COMPARATOR);
		//System.out.println("\nNames, case sensitive ascending order (NAME_COMPARATOR)");
		//displayFiles(listOfFiles);

        List<File> oneFile = new LinkedList<File>();

        for ( int i = 0; i < listOfFiles.length; i++ ) {
            if ( listOfFiles[i].isFile() ) {
                log.debug( "File " + listOfFiles[i].getName() );
                list.add( listOfFiles[i] );
                oneFile.add( list.get(i) );
            } else if (listOfFiles[i].isDirectory()) {
                log.debug("Directory " + listOfFiles[i].getName());
            }
        }

        //List<File> oneFile = new LinkedList<File>();
        //log.debug( "file " + list.get(0) );
        //log.debug( "file " + list.get(0) );
        //log.debug( "file list size " + list.size() );

        Context context = new Context( params );
        BlockFileLoader bfl = new BlockFileLoader( params, oneFile );

        Script script = null;
        ScriptType script3 = null;
        TransactionOutput trxOut = null;
        List<ScriptChunk> scriptChunks = null;

        String btc_address = null;
        int blkNum = 0;

        for ( Block block : bfl ) {

            try {

                blkNum++;
                trxs = block.getTransactions();

                log.debug( "block " + blkNum + 
                           ": time " + sdf.format(block.getTime()) +
                           ": trxs " + trxs.size() );

                //log.debug( "block.getHashAsString " + block.getHashAsString() );
                //log.debug( "block date time "+sdf.format(block.getTime()) );
                //log.debug( "num of trxs "+trxs.size() );

                for( int x=0; x<trxs.size() ; x++ ) {

                    trx = trxs.get(x);
                    trxOutputs = trx.getOutputs();

                    for ( int y = 0; y < trxOutputs.size(); y++ ) {

                        script3 = trx.getOutput( y ).getScriptPubKey().getScriptType();
                        trxOutputs = trx.getOutputs();

                        for( int z = 0; z < trxOutputs.size(); z++ ) { 

                            trxOut = trxOutputs.get( z );
                            ScriptType scriptTypeEnum =  trx.getOutput( z ).getScriptPubKey().getScriptType();

                            if ( scriptTypeEnum == Script.ScriptType.P2PKH ) {  // <-----------P2PKH

                                script = trxOut.getScriptPubKey();

                                duplicate_address_check( script_getToAddress( script ) );

                            } else if ( scriptTypeEnum == Script.ScriptType.P2PK ) { // <------P2PK

                                script = trxOut.getScriptPubKey();

                                scriptChunks = trx.getOutput( z ).getScriptPubKey().getChunks();
                                String chunkZero = ""+scriptChunks.get( z );
                                        
                                trxOut = trxOutputs.get( z );
                                script = trxOut.getScriptPubKey();
                                chunkZero = script.toString();

                                int leftBracket = chunkZero.indexOf("[");
                                int rightBracket = chunkZero.indexOf("]");
                                String pubKey = chunkZero.substring(leftBracket+1, rightBracket);

                                duplicate_address_check( addressFromPubKey2( pubKey ) );

                            } else if ( scriptTypeEnum == Script.ScriptType.P2SH ) { // <---P2SH

                                log.debug("##P2PSH");

                            } else if ( scriptTypeEnum == Script.ScriptType.P2WPKH ) { // <--P2WPKH

                                log.debug("##P2WPKH");

                            } else if ( scriptTypeEnum == Script.ScriptType.P2WSH ) { // <---P2WSH

                                log.debug("##P2WSH");

                            } else {

                                log.debug( trx.toString() );

                            }
                        }
                    }
                }
            } catch ( Exception ax ) {
                log.debug( ax.toString() );
            }
        }
    }

    /****
     * 
     * @param btc_address
     */
    public void duplicate_address_check( String btc_address ) {
        if ( addrSet.contains( btc_address ) ) {
            //log.debug( "duplicate " + btc_address );
        } else {
            //log.debug( "addr " + btc_address );
            //log.debug( "" + address_id + "," + "\"" + btc_address + "\"" );
            System.out.println( "" + address_id + "," + btc_address );
            addrSet.add( btc_address );
            address_id++;
        }
    }

    /******
     * 
     * @param script
     * @return
     */
    public static String script_getToAddress( Script script ) {
        String addressStr = "not set";
        try {
            addressStr = script.getToAddress( params ).toString();
        }catch(Exception exx){
            log.debug("script_getToAddress exception "+exx.toString());
        }
        return addressStr;
    }

    /*****
     * 
     * @param pubKey
     * @return
     */
    public static String addressFromPubKey2( String pubKey ) {
        String addr = "net set";
        try {
            addr = addressFromPubKey( pubKey );
        }catch(Exception exx){
            log.debug("addressFromPubKey exception "+exx.toString());
        }
        return addr;
    }

    /***
     * 
     */
    //public String addressFromPubKey( String a ){
    public static String addressFromPubKey( String a ){
        String result = "addr not found";
        try {
            //System.out.println("learn me btc: https://learnmeabitcoin.com/technical/hash-function");
            //https://gobittest.appspot.com/Address
            byte[] iti = hexStringToByteArray(a);
            Sha256Hash itiHash = Sha256Hash.of(iti);

            MessageDigest rmdx = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1x = rmdx.digest(itiHash.getBytes());
            //Converting the byte array in to HexString format
            StringBuffer hexString = new StringBuffer();
            for (int i = 0;i<r1x.length;i++) {
                hexString.append(Integer.toHexString(0xFF & r1x[i]));
            }

            byte[] zero = hexStringToByteArray("00");
            byte[] s5 = new byte[r1x.length + 1];
            s5[0] = zero[0];
            System.arraycopy(r1x, 0, s5, 1, r1x.length);
            
            Sha256Hash s5x = Sha256Hash.of(s5);
            Sha256Hash s6x = Sha256Hash.of(s5x.getBytes());

            byte[] first4of6 = new byte[4];
            System.arraycopy(s6x.getBytes(), 0, first4of6, 0, 4);

            byte[] s8 = new byte[s5.length + first4of6.length];

            System.arraycopy(s5, 0, s8, 0, s5.length);
            System.arraycopy(first4of6, 0, s8, s5.length, first4of6.length);

            //7 - First four bytes of 6
            result = Base58.encode(s8);
        } catch(Exception ex) {
            System.out.println("address_From_Pub_Key  "+ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    /* s must be an even-length string. */
    //https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java/140861#140861
    /***
     * 
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

	public static void displayFiles(File[] files) {
		for (File file : files) {
			System.out.printf("File: %-20s Last Modified:" + new Date(file.lastModified()) + "\n", file.getName());
		}
	}

}
