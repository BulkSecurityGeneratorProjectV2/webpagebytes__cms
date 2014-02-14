package com.webbricks.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import com.webbricks.cache.WBCacheInstances;
import com.webbricks.cache.WBFilesCache;
import com.webbricks.cmsdata.WBFile;
import com.webbricks.datautility.WBBlobHandler;
import com.webbricks.datautility.WBGaeBlobHandler;
import com.webbricks.exception.WBException;
import com.webbricks.exception.WBIOException;

public class FileContentBuilder {
	private WBCacheInstances cacheInstances;
	private WBBlobHandler blobHandler;
	private WBFilesCache filesCache;
	public FileContentBuilder(WBCacheInstances cacheInstances)
	{
		this.cacheInstances = cacheInstances;
		blobHandler = new WBGaeBlobHandler();
		filesCache = cacheInstances.getWBFilesCache();
	}
	public void initialize()
	{
	}
	
	public WBFile find(String externalKey) throws WBException
	{
		return filesCache.getByExternalKey(externalKey);
	}
	public InputStream getFileContent(WBFile file) throws WBException
	{
		return blobHandler.getBlobData(file.getBlobKey());
	}
	public void writeFileContent(WBFile wbFile, OutputStream os) throws WBException 
	{
		InputStream is = blobHandler.getBlobData(wbFile.getBlobKey());
		byte[] buffer = new byte[100*1024];
		int len;
		try 
		{
			while ((len = is.read(buffer)) != -1) {
			    os.write(buffer, 0, len);
			}
		} catch (IOException e)
		{
			throw new WBIOException(e.getMessage(), e);
		}
	}
}
