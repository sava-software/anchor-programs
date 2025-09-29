package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.lang.Boolean;
import java.lang.String;

import java.util.OptionalInt;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record StateModel(AccountType accountType,
                         byte[] name,
                         String uri, byte[] _uri,
                         Boolean enabled,
                         PublicKey[] assets,
                         CreatedModel created,
                         PublicKey owner,
                         byte[] portfolioManagerName,
                         PublicKey[] borrowable,
                         OptionalInt timelockDuration,
                         IntegrationAcl[] integrationAcls,
                         DelegateAcl[] delegateAcls) implements Borsh {

  public static StateModel createRecord(final AccountType accountType,
                                        final byte[] name,
                                        final String uri,
                                        final Boolean enabled,
                                        final PublicKey[] assets,
                                        final CreatedModel created,
                                        final PublicKey owner,
                                        final byte[] portfolioManagerName,
                                        final PublicKey[] borrowable,
                                        final OptionalInt timelockDuration,
                                        final IntegrationAcl[] integrationAcls,
                                        final DelegateAcl[] delegateAcls) {
    return new StateModel(accountType,
                          name,
                          uri, Borsh.getBytes(uri),
                          enabled,
                          assets,
                          created,
                          owner,
                          portfolioManagerName,
                          borrowable,
                          timelockDuration,
                          integrationAcls,
                          delegateAcls);
  }

  public static StateModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var accountType = _data[i++] == 0 ? null : AccountType.read(_data, i);
    if (accountType != null) {
      i += Borsh.len(accountType);
    }
    final var name = _data[i++] == 0 ? null : new byte[32];
    if (name != null) {
      i += Borsh.readArray(name, _data, i);
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
    final var created = _data[i++] == 0 ? null : CreatedModel.read(_data, i);
    if (created != null) {
      i += Borsh.len(created);
    }
    final var owner = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (owner != null) {
      i += 32;
    }
    final var portfolioManagerName = _data[i++] == 0 ? null : new byte[32];
    if (portfolioManagerName != null) {
      i += Borsh.readArray(portfolioManagerName, _data, i);
    }
    final var borrowable = _data[i++] == 0 ? null : Borsh.readPublicKeyVector(_data, i);
    if (borrowable != null) {
      i += Borsh.lenVector(borrowable);
    }
    final var timelockDuration = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (timelockDuration.isPresent()) {
      i += 4;
    }
    final var integrationAcls = _data[i++] == 0 ? null : Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, i);
    if (integrationAcls != null) {
      i += Borsh.lenVector(integrationAcls);
    }
    final var delegateAcls = _data[i++] == 0 ? null : Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    return new StateModel(accountType,
                          name,
                          uri, Borsh.getBytes(uri),
                          enabled,
                          assets,
                          created,
                          owner,
                          portfolioManagerName,
                          borrowable,
                          timelockDuration,
                          integrationAcls,
                          delegateAcls);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(accountType, _data, i);
    if (name == null || name.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArrayChecked(name, 32, _data, i);
    }
    i += Borsh.writeOptionalVector(_uri, _data, i);
    i += Borsh.writeOptional(enabled, _data, i);
    if (assets == null || assets.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(assets, _data, i);
    }
    i += Borsh.writeOptional(created, _data, i);
    i += Borsh.writeOptional(owner, _data, i);
    if (portfolioManagerName == null || portfolioManagerName.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeArrayChecked(portfolioManagerName, 32, _data, i);
    }
    if (borrowable == null || borrowable.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(borrowable, _data, i);
    }
    i += Borsh.writeOptional(timelockDuration, _data, i);
    if (integrationAcls == null || integrationAcls.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(integrationAcls, _data, i);
    }
    if (delegateAcls == null || delegateAcls.length == 0) {
      _data[i++] = 0;
    } else {
      _data[i++] = 1;
      i += Borsh.writeVector(delegateAcls, _data, i);
    }
    return i - offset;
  }

  @Override
  public int l() {
    return (accountType == null ? 1 : (1 + Borsh.len(accountType)))
         + (name == null || name.length == 0 ? 1 : (1 + Borsh.lenArray(name)))
         + (_uri == null || _uri.length == 0 ? 1 : (1 + Borsh.lenVector(_uri)))
         + (enabled == null ? 1 : (1 + 1))
         + (assets == null || assets.length == 0 ? 1 : (1 + Borsh.lenVector(assets)))
         + (created == null ? 1 : (1 + Borsh.len(created)))
         + (owner == null ? 1 : (1 + 32))
         + (portfolioManagerName == null || portfolioManagerName.length == 0 ? 1 : (1 + Borsh.lenArray(portfolioManagerName)))
         + (borrowable == null || borrowable.length == 0 ? 1 : (1 + Borsh.lenVector(borrowable)))
         + (timelockDuration == null || timelockDuration.isEmpty() ? 1 : (1 + 4))
         + (integrationAcls == null || integrationAcls.length == 0 ? 1 : (1 + Borsh.lenVector(integrationAcls)))
         + (delegateAcls == null || delegateAcls.length == 0 ? 1 : (1 + Borsh.lenVector(delegateAcls)));
  }
}
