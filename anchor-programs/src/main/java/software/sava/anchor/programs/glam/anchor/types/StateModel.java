package software.sava.anchor.programs.glam.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import java.util.OptionalInt;

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
                         MintModel[] mints,
                         CompanyModel company,
                         ManagerModel owner,
                         CreatedModel created,
                         PublicKey baseAsset,
                         OptionalInt updateTimelock,
                         TimeUnit timeUnit,
                         DelegateAcl[] delegateAcls,
                         Integration[] integrations,
                         PublicKey[] borrowableAssets,
                         int[] driftMarketIndexesPerp,
                         int[] driftMarketIndexesSpot,
                         int[] driftOrderTypes,
                         PublicKey[] kaminoLendingMarkets,
                         PublicKey[] meteoraDlmmPools,
                         OptionalInt maxSwapSlippageBps,
                         PublicKey[] driftVaultsAllowlist,
                         PublicKey[] kaminoVaultsAllowlist,
                         Metadata metadata,
                         FundOpenfundsModel rawOpenfunds) implements Borsh {

  public static StateModel createRecord(final PublicKey id,
                                        final AccountType accountType,
                                        final String name,
                                        final String uri,
                                        final Boolean enabled,
                                        final PublicKey[] assets,
                                        final MintModel[] mints,
                                        final CompanyModel company,
                                        final ManagerModel owner,
                                        final CreatedModel created,
                                        final PublicKey baseAsset,
                                        final OptionalInt updateTimelock,
                                        final TimeUnit timeUnit,
                                        final DelegateAcl[] delegateAcls,
                                        final Integration[] integrations,
                                        final PublicKey[] borrowableAssets,
                                        final int[] driftMarketIndexesPerp,
                                        final int[] driftMarketIndexesSpot,
                                        final int[] driftOrderTypes,
                                        final PublicKey[] kaminoLendingMarkets,
                                        final PublicKey[] meteoraDlmmPools,
                                        final OptionalInt maxSwapSlippageBps,
                                        final PublicKey[] driftVaultsAllowlist,
                                        final PublicKey[] kaminoVaultsAllowlist,
                                        final Metadata metadata,
                                        final FundOpenfundsModel rawOpenfunds) {
    return new StateModel(id,
                          accountType,
                          name, Borsh.getBytes(name),
                          uri, Borsh.getBytes(uri),
                          enabled,
                          assets,
                          mints,
                          company,
                          owner,
                          created,
                          baseAsset,
                          updateTimelock,
                          timeUnit,
                          delegateAcls,
                          integrations,
                          borrowableAssets,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          kaminoLendingMarkets,
                          meteoraDlmmPools,
                          maxSwapSlippageBps,
                          driftVaultsAllowlist,
                          kaminoVaultsAllowlist,
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
    final var mints = _data[i++] == 0 ? null : Borsh.readVector(MintModel.class, MintModel::read, _data, i);
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
    final var baseAsset = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (baseAsset != null) {
      i += 32;
    }
    final var updateTimelock = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (updateTimelock.isPresent()) {
      i += 4;
    }
    final var timeUnit = _data[i++] == 0 ? null : TimeUnit.read(_data, i);
    if (timeUnit != null) {
      i += Borsh.len(timeUnit);
    }
    final var delegateAcls = _data[i++] == 0 ? null : Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    if (delegateAcls != null) {
      i += Borsh.lenVector(delegateAcls);
    }
    final var integrations = _data[i++] == 0 ? null : Borsh.readVector(Integration.class, Integration::read, _data, i);
    if (integrations != null) {
      i += Borsh.lenVector(integrations);
    }
    final var borrowableAssets = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (borrowableAssets != null) {
      i += Borsh.lenVector(borrowableAssets);
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
    final var kaminoLendingMarkets = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (kaminoLendingMarkets != null) {
      i += Borsh.lenVector(kaminoLendingMarkets);
    }
    final var meteoraDlmmPools = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (meteoraDlmmPools != null) {
      i += Borsh.lenVector(meteoraDlmmPools);
    }
    final var maxSwapSlippageBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (maxSwapSlippageBps.isPresent()) {
      i += 4;
    }
    final var driftVaultsAllowlist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (driftVaultsAllowlist != null) {
      i += Borsh.lenVector(driftVaultsAllowlist);
    }
    final var kaminoVaultsAllowlist = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (kaminoVaultsAllowlist != null) {
      i += Borsh.lenVector(kaminoVaultsAllowlist);
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
                          mints,
                          company,
                          owner,
                          created,
                          baseAsset,
                          updateTimelock,
                          timeUnit,
                          delegateAcls,
                          integrations,
                          borrowableAssets,
                          driftMarketIndexesPerp,
                          driftMarketIndexesSpot,
                          driftOrderTypes,
                          kaminoLendingMarkets,
                          meteoraDlmmPools,
                          maxSwapSlippageBps,
                          driftVaultsAllowlist,
                          kaminoVaultsAllowlist,
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
    if (mints == null || mints.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(mints, _data, i);
    }
    i += Borsh.writeOptional(company, _data, i);
    i += Borsh.writeOptional(owner, _data, i);
    i += Borsh.writeOptional(created, _data, i);
    i += Borsh.writeOptional(baseAsset, _data, i);
    i += Borsh.writeOptional(updateTimelock, _data, i);
    i += Borsh.writeOptional(timeUnit, _data, i);
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
    if (borrowableAssets == null || borrowableAssets.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(borrowableAssets, _data, i);
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
    if (kaminoLendingMarkets == null || kaminoLendingMarkets.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(kaminoLendingMarkets, _data, i);
    }
    if (meteoraDlmmPools == null || meteoraDlmmPools.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(meteoraDlmmPools, _data, i);
    }
    i += Borsh.writeOptional(maxSwapSlippageBps, _data, i);
    if (driftVaultsAllowlist == null || driftVaultsAllowlist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(driftVaultsAllowlist, _data, i);
    }
    if (kaminoVaultsAllowlist == null || kaminoVaultsAllowlist.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(kaminoVaultsAllowlist, _data, i);
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
         + (mints == null || mints.length == 0 ? 1 : (1 + Borsh.lenVector(mints)))
         + (company == null ? 1 : (1 + Borsh.len(company)))
         + (owner == null ? 1 : (1 + Borsh.len(owner)))
         + (created == null ? 1 : (1 + Borsh.len(created)))
         + (baseAsset == null ? 1 : (1 + 32))
         + (updateTimelock == null || updateTimelock.isEmpty() ? 1 : (1 + 4))
         + (timeUnit == null ? 1 : (1 + Borsh.len(timeUnit)))
         + (delegateAcls == null || delegateAcls.length == 0 ? 1 : (1 + Borsh.lenVector(delegateAcls)))
         + (integrations == null || integrations.length == 0 ? 1 : (1 + Borsh.lenVector(integrations)))
         + (borrowableAssets == null || borrowableAssets.length == 0 ? 1 : (1 + Borsh.lenVector(borrowableAssets)))
         + (driftMarketIndexesPerp == null || driftMarketIndexesPerp.length == 0 ? 1 : (1 + Borsh.lenVector(driftMarketIndexesPerp)))
         + (driftMarketIndexesSpot == null || driftMarketIndexesSpot.length == 0 ? 1 : (1 + Borsh.lenVector(driftMarketIndexesSpot)))
         + (driftOrderTypes == null || driftOrderTypes.length == 0 ? 1 : (1 + Borsh.lenVector(driftOrderTypes)))
         + (kaminoLendingMarkets == null || kaminoLendingMarkets.length == 0 ? 1 : (1 + Borsh.lenVector(kaminoLendingMarkets)))
         + (meteoraDlmmPools == null || meteoraDlmmPools.length == 0 ? 1 : (1 + Borsh.lenVector(meteoraDlmmPools)))
         + (maxSwapSlippageBps == null || maxSwapSlippageBps.isEmpty() ? 1 : (1 + 4))
         + (driftVaultsAllowlist == null || driftVaultsAllowlist.length == 0 ? 1 : (1 + Borsh.lenVector(driftVaultsAllowlist)))
         + (kaminoVaultsAllowlist == null || kaminoVaultsAllowlist.length == 0 ? 1 : (1 + Borsh.lenVector(kaminoVaultsAllowlist)))
         + (metadata == null ? 1 : (1 + Borsh.len(metadata)))
         + (rawOpenfunds == null ? 1 : (1 + Borsh.len(rawOpenfunds)));
  }
}
