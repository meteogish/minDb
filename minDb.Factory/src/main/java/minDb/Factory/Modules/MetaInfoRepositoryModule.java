package minDb.Factory.Modules;

import com.google.inject.AbstractModule;

import minDb.Core.Components.IMetaInfoRepository;
import minDb.MetaInfoRepository.JsonMetaInfoRepository;

/**
 * MetaInfoRepositoryModule
 */
public class MetaInfoRepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IMetaInfoRepository.class).to(JsonMetaInfoRepository.class);
	}


}