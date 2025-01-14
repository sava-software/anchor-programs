package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record StateModel(PublicKey id,
                         String name, byte[] _name,
                         String uri, byte[] _uri,
                         String metadataUri, byte[] _metadataUri,
                         Boolean isEnabled,
                         PublicKey[] assets,
                         PublicKey[] externalVaultAccounts,
                         ShareClassModel[] mints,
                         CompanyModel company,
                         ManagerModel owner,
                         CreatedModel created,
                         DelegateAcl[] delegateAcls,
                         IntegrationAcl[] integrationAcls,
                         int[] driftMarketIndexesPerp,
                         int[] driftMarketIndexesSpot,
                         int[] driftOrderTypes,
                         boolean isRawOpenfunds,
                         FundOpenfundsModel rawOpenfunds) implements Borsh {

  public static StateModel createRecord(final PublicKey id,
                                        final String name,
                                        final String uri,
                                        final String metadataUri,
                                        final Boolean isEnabled,
                                        final PublicKey[] assets,
                                        final PublicKey[] externalVaultAccounts,
                                        final ShareClassModel[] mints,
                                        final CompanyModel company,
                                        final ManagerModel owner,
                                        final CreatedModel created,
                                        final DelegateAcl[] delegateAcls,
                                        final IntegrationAcl[] integrationAcls,
                                        final int[] driftMarketIndexesPerp,
                                        final int[] driftMarketIndexesSpot,
                                        final int[] driftOrderTypes,
                                        final boolean isRawOpenfunds,
                                        final FundOpenfundsModel rawOpenfunds) {
    return new StateModel(id,
                          name, Borsh.getBytes(name),
                          uri, Borsh.getBytes(uri),
                          metadataUri, Borsh.getBytes(metadataUri),
                          isEnabled,
                          assets,
                          externalVaultAccounts,
                          mints,
                          company,
                          owner,
                          created,
                          delegateAcls,
                          integrationAcls,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          isRawOpenfunds,
                          rawOpenfunds);
  }

  public static StateModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var id = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (id != null) {
      i += 32;
    }
    final var name = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (name != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var metadataUri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (metadataUri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var isEnabled = _data[i++] == 0 ? null : _data[i] == 1;
    if (isEnabled != null) {
      ++i;
    }
    final var assets = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(assets);
    final var externalVaultAccounts = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(externalVaultAccounts);
    final var mints = Borsh.readVector(ShareClassModel.class, ShareClassModel::read, _data, i);
    i += Borsh.lenVector(mints);
    final var company = _data[i++] == 0 ? null : CompanyModel.read(_data, i);
    if (company != null) {
      i += Borsh.len(company);
    }
    final var owner = _data[i++] == 0 ? null : ManagerModel.read(_data, i);
    if (owner != null) {
      i += Borsh.len(owner);
    }
    final var created = _data[i++] == 0 ? null : CreatedModel.read(_data, i);
    if (created != null) {
      i += Borsh.len(created);
    }
    final var delegateAcls = Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    i += Borsh.lenVector(delegateAcls);
    final var integrationAcls = Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, i);
    i += Borsh.lenVector(integrationAcls);
    final var driftMarketIndexesPerp = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftMarketIndexesPerp);
    final var driftMarketIndexesSpot = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftMarketIndexesSpot);
    final var driftOrderTypes = Borsh.readintVector(_data, i);
    i += Borsh.lenVector(driftOrderTypes);
    final var isRawOpenfunds = _data[i] == 1;
    ++i;
    final var rawOpenfunds = _data[i++] == 0 ? null : FundOpenfundsModel.read(_data, i);
    return new StateModel(id,
                          name, Borsh.getBytes(name),
                          uri, Borsh.getBytes(uri),
                          metadataUri, Borsh.getBytes(metadataUri),
                          isEnabled,
                          assets,
                          externalVaultAccounts,
                          mints,
                          company,
                          owner,
                          created,
                          delegateAcls,
                          integrationAcls,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          isRawOpenfunds,
                          rawOpenfunds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(id, _data, i);
    i += Borsh.writeOptionalVector(_name, _data, i);
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptionalVector(_metadataUri, _data, i);
    i += Borsh.writeOptional(isEnabled, _data, i);
    i += Borsh.writeVector(assets, _data, i);
    i += Borsh.writeVector(externalVaultAccounts, _data, i);
    i += Borsh.writeVector(mints, _data, i);
    i += Borsh.writeOptional(company, _data, i);
    i += Borsh.writeOptional(owner, _data, i);
    i += Borsh.writeOptional(created, _data, i);
    i += Borsh.writeVector(delegateAcls, _data, i);
    i += Borsh.writeVector(integrationAcls, _data, i);
    i += Borsh.writeVector(driftMarketIndexesPerp, _data, i);
    i += Borsh.writeVector(driftMarketIndexesSpot, _data, i);
    i += Borsh.writeVector(driftOrderTypes, _data, i);
    _data[i] = (byte) (isRawOpenfunds ? 1 : 0);
    ++i;
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (id == null ? 1 : (1 + 32))
         + (_name == null || _name.length == 0 ? 1 : (1 + Borsh.lenVector(_name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (_metadataUri == null || _metadataUri.length == 0 ? 1 : (1 + Borsh.lenVector(_metadataUri)))
         + (isEnabled == null ? 1 : (1 + 1))
         + Borsh.lenVector(assets)
         + Borsh.lenVector(externalVaultAccounts)
         + Borsh.lenVector(mints)
         + (company == null ? 1 : (1 + Borsh.len(company)))
         + (owner == null ? 1 : (1 + Borsh.len(owner)))
         + (created == null ? 1 : (1 + Borsh.len(created)))
         + Borsh.lenVector(delegateAcls)
         + Borsh.lenVector(integrationAcls)
         + Borsh.lenVector(driftMarketIndexesPerp)
         + Borsh.lenVector(driftMarketIndexesSpot)
         + Borsh.lenVector(driftOrderTypes)
         + 1
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
