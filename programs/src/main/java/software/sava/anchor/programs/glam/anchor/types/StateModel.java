package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record StateModel(PublicKey id,
                         AccountType accountType,
                         String name, byte[] _name,
                         String uri, byte[] _uri,
                         Boolean enabled,
                         PublicKey[] assets,
                         PublicKey[] externalVaultAccounts,
                         ShareClassModel[] mints,
                         CompanyModel company,
                         ManagerModel owner,
                         CreatedModel created,
                         DelegateAcl[] delegateAcls,
                         Integration[] integrations,
                         int[] driftMarketIndexesPerp,
                         int[] driftMarketIndexesSpot,
                         int[] driftOrderTypes,
                         Metadata metadata,
                         FundOpenfundsModel rawOpenfunds) implements Borsh {

  public static StateModel createRecord(final PublicKey id,
                                        final AccountType accountType,
                                        final String name,
                                        final String uri,
                                        final Boolean enabled,
                                        final PublicKey[] assets,
                                        final PublicKey[] externalVaultAccounts,
                                        final ShareClassModel[] mints,
                                        final CompanyModel company,
                                        final ManagerModel owner,
                                        final CreatedModel created,
                                        final DelegateAcl[] delegateAcls,
                                        final Integration[] integrations,
                                        final int[] driftMarketIndexesPerp,
                                        final int[] driftMarketIndexesSpot,
                                        final int[] driftOrderTypes,
                                        final Metadata metadata,
                                        final FundOpenfundsModel rawOpenfunds) {
    return new StateModel(id,
                          accountType,
                          name, Borsh.getBytes(name),
                          uri, Borsh.getBytes(uri),
                          enabled,
                          assets,
                          externalVaultAccounts,
                          mints,
                          company,
                          owner,
                          created,
                          delegateAcls,
                          integrations,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          metadata,
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
    final var accountType = _data[i++] == 0 ? null : AccountType.read(_data, i);
    if (accountType != null) {
      i += Borsh.len(accountType);
    }
    final var name = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (name != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var uri = _data[i++] == 0 ? null : Borsh.string(_data, i);
    if (uri != null) {
      i += (Integer.BYTES + getInt32LE(_data, i));
    }
    final var enabled = _data[i++] == 0 ? null : _data[i] == 1;
    if (enabled != null) {
      ++i;
    }
    final var assets = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (assets != null) {
      i += Borsh.lenVector(assets);
    }
    final var externalVaultAccounts = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (externalVaultAccounts != null) {
      i += Borsh.lenVector(externalVaultAccounts);
    }
    final var mints = _data[i++] == 0 ? null : Borsh.readVector(ShareClassModel.class, ShareClassModel::read, _data, i);
    if (mints != null) {
      i += Borsh.lenVector(mints);
    }
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
    final var delegateAcls = _data[i++] == 0 ? null : Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    if (delegateAcls != null) {
      i += Borsh.lenVector(delegateAcls);
    }
    final var integrations = _data[i++] == 0 ? null : Borsh.readVector(Integration.class, Integration::read, _data, i);
    if (integrations != null) {
      i += Borsh.lenVector(integrations);
    }
    final var driftMarketIndexesPerp = _data[i++] == 0 ? null : Borsh.readintVector(_data, i);
    if (driftMarketIndexesPerp != null) {
      i += Borsh.lenVector(driftMarketIndexesPerp);
    }
    final var driftMarketIndexesSpot = _data[i++] == 0 ? null : Borsh.readintVector(_data, i);
    if (driftMarketIndexesSpot != null) {
      i += Borsh.lenVector(driftMarketIndexesSpot);
    }
    final var driftOrderTypes = _data[i++] == 0 ? null : Borsh.readintVector(_data, i);
    if (driftOrderTypes != null) {
      i += Borsh.lenVector(driftOrderTypes);
    }
    final var metadata = _data[i++] == 0 ? null : Metadata.read(_data, i);
    if (metadata != null) {
      i += Borsh.len(metadata);
    }
    final var rawOpenfunds = _data[i++] == 0 ? null : FundOpenfundsModel.read(_data, i);
    return new StateModel(id,
                          accountType,
                          name, Borsh.getBytes(name),
                          uri, Borsh.getBytes(uri),
                          enabled,
                          assets,
                          externalVaultAccounts,
                          mints,
                          company,
                          owner,
                          created,
                          delegateAcls,
                          integrations,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          metadata,
                          rawOpenfunds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(id, _data, i);
    i += Borsh.writeOptional(accountType, _data, i);
    i += Borsh.writeOptionalVector(_name, _data, i);
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptional(enabled, _data, i);
    if (assets == null || assets.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(assets, _data, i);
    }
    if (externalVaultAccounts == null || externalVaultAccounts.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(externalVaultAccounts, _data, i);
    }
    if (mints == null || mints.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(mints, _data, i);
    }
    i += Borsh.writeOptional(company, _data, i);
    i += Borsh.writeOptional(owner, _data, i);
    i += Borsh.writeOptional(created, _data, i);
    if (delegateAcls == null || delegateAcls.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(delegateAcls, _data, i);
    }
    if (integrations == null || integrations.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(integrations, _data, i);
    }
    if (driftMarketIndexesPerp == null || driftMarketIndexesPerp.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(driftMarketIndexesPerp, _data, i);
    }
    if (driftMarketIndexesSpot == null || driftMarketIndexesSpot.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(driftMarketIndexesSpot, _data, i);
    }
    if (driftOrderTypes == null || driftOrderTypes.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(driftOrderTypes, _data, i);
    }
    i += Borsh.writeOptional(metadata, _data, i);
    i += Borsh.writeOptional(rawOpenfunds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (id == null ? 1 : (1 + 32))
         + (accountType == null ? 1 : (1 + Borsh.len(accountType)))
         + (_name == null || _name.length == 0 ? 1 : (1 + Borsh.lenVector(_name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (enabled == null ? 1 : (1 + 1))
         + (assets == null || assets.length == 0 ? 1 : (1 + Borsh.lenVector(assets)))
         + (externalVaultAccounts == null || externalVaultAccounts.length == 0 ? 1 : (1 + Borsh.lenVector(externalVaultAccounts)))
         + (mints == null || mints.length == 0 ? 1 : (1 + Borsh.lenVector(mints)))
         + (company == null ? 1 : (1 + Borsh.len(company)))
         + (owner == null ? 1 : (1 + Borsh.len(owner)))
         + (created == null ? 1 : (1 + Borsh.len(created)))
         + (delegateAcls == null || delegateAcls.length == 0 ? 1 : (1 + Borsh.lenVector(delegateAcls)))
         + (integrations == null || integrations.length == 0 ? 1 : (1 + Borsh.lenVector(integrations)))
         + (driftMarketIndexesPerp == null || driftMarketIndexesPerp.length == 0 ? 1 : (1 + Borsh.lenVector(driftMarketIndexesPerp)))
         + (driftMarketIndexesSpot == null || driftMarketIndexesSpot.length == 0 ? 1 : (1 + Borsh.lenVector(driftMarketIndexesSpot)))
         + (driftOrderTypes == null || driftOrderTypes.length == 0 ? 1 : (1 + Borsh.lenVector(driftOrderTypes)))
         + (metadata == null ? 1 : (1 + Borsh.len(metadata)))
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
