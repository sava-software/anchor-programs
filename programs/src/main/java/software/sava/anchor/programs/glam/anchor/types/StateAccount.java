package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record StateAccount(PublicKey _address,
                           Discriminator discriminator,
                           AccountType accountType,
                           PublicKey owner,
                           PublicKey vault,
                           boolean enabled,
                           CreatedModel created,
                           PublicKey engine,
                           PublicKey[] mints,
                           Metadata metadata,
                           String name, byte[] _name,
                           String uri, byte[] _uri,
                           PublicKey[] assets,
                           DelegateAcl[] delegateAcls,
                           Integration[] integrations,
                           EngineField[][] params) implements Borsh {

  public static final Discriminator DISCRIMINATOR = toDiscriminator(142, 247, 54, 95, 85, 133, 249, 103);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ACCOUNT_TYPE_OFFSET = 8;
  public static final int OWNER_OFFSET = 9;
  public static final int VAULT_OFFSET = 41;
  public static final int ENABLED_OFFSET = 73;
  public static final int CREATED_OFFSET = 74;
  public static final int ENGINE_OFFSET = 122;
  public static final int MINTS_OFFSET = 154;

  public static Filter createAccountTypeFilter(final AccountType accountType) {
    return Filter.createMemCompFilter(ACCOUNT_TYPE_OFFSET, accountType.write());
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createEnabledFilter(final boolean enabled) {
    return Filter.createMemCompFilter(ENABLED_OFFSET, new byte[]{(byte) (enabled ? 1 : 0)});
  }

  public static Filter createCreatedFilter(final CreatedModel created) {
    return Filter.createMemCompFilter(CREATED_OFFSET, created.write());
  }

  public static Filter createEngineFilter(final PublicKey engine) {
    return Filter.createMemCompFilter(ENGINE_OFFSET, engine);
  }

  public static StateAccount createRecord(final PublicKey _address,
                                          final Discriminator discriminator,
                                          final AccountType accountType,
                                          final PublicKey owner,
                                          final PublicKey vault,
                                          final boolean enabled,
                                          final CreatedModel created,
                                          final PublicKey engine,
                                          final PublicKey[] mints,
                                          final Metadata metadata,
                                          final String name,
                                          final String uri,
                                          final PublicKey[] assets,
                                          final DelegateAcl[] delegateAcls,
                                          final Integration[] integrations,
                                          final EngineField[][] params) {
    return new StateAccount(_address,
                            discriminator,
                            accountType,
                            owner,
                            vault,
                            enabled,
                            created,
                            engine,
                            mints,
                            metadata,
                            name, name.getBytes(UTF_8),
                            uri, uri.getBytes(UTF_8),
                            assets,
                            delegateAcls,
                            integrations,
                            params);
  }

  public static StateAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static StateAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], StateAccount> FACTORY = StateAccount::read;

  public static StateAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var accountType = AccountType.read(_data, i);
    i += Borsh.len(accountType);
    final var owner = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var enabled = _data[i] == 1;
    ++i;
    final var created = CreatedModel.read(_data, i);
    i += Borsh.len(created);
    final var engine = readPubKey(_data, i);
    i += 32;
    final var mints = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(mints);
    final var metadata = _data[i++] == 0 ? null : Metadata.read(_data, i);
    if (metadata != null) {
      i += Borsh.len(metadata);
    }
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var assets = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(assets);
    final var delegateAcls = Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    i += Borsh.lenVector(delegateAcls);
    final var integrations = Borsh.readVector(Integration.class, Integration::read, _data, i);
    i += Borsh.lenVector(integrations);
    final var params = Borsh.readMultiDimensionVector(EngineField.class, EngineField::read, _data, i);
    return new StateAccount(_address,
                            discriminator,
                            accountType,
                            owner,
                            vault,
                            enabled,
                            created,
                            engine,
                            mints,
                            metadata,
                            name, name.getBytes(UTF_8),
                            uri, uri.getBytes(UTF_8),
                            assets,
                            delegateAcls,
                            integrations,
                            params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(accountType, _data, i);
    owner.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    _data[i] = (byte) (enabled ? 1 : 0);
    ++i;
    i += Borsh.write(created, _data, i);
    engine.write(_data, i);
    i += 32;
    i += Borsh.writeVector(mints, _data, i);
    i += Borsh.writeOptional(metadata, _data, i);
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    i += Borsh.writeVector(assets, _data, i);
    i += Borsh.writeVector(delegateAcls, _data, i);
    i += Borsh.writeVector(integrations, _data, i);
    i += Borsh.writeVector(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len(accountType)
         + 32
         + 32
         + 1
         + Borsh.len(created)
         + 32
         + Borsh.lenVector(mints)
         + (metadata == null ? 1 : (1 + Borsh.len(metadata)))
         + Borsh.lenVector(_name)
         + Borsh.lenVector(_uri)
         + Borsh.lenVector(assets)
         + Borsh.lenVector(delegateAcls)
         + Borsh.lenVector(integrations)
         + Borsh.lenVector(params);
  }
}
