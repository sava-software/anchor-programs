package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record StateAccount(PublicKey _address,
                           Discriminator discriminator,
                           AccountType accountType,
                           boolean enabled,
                           PublicKey vault,
                           PublicKey owner,
                           byte[] portfolioManagerName,
                           CreatedModel created,
                           PublicKey baseAssetMint,
                           int baseAssetDecimals,
                           int baseAssetTokenProgram,
                           byte[] name,
                           int timelockDuration,
                           long timelockExpiresAt,
                           PublicKey mint,
                           PublicKey[] assets,
                           IntegrationAcl[] integrationAcls,
                           DelegateAcl[] delegateAcls,
                           PublicKey[] externalPositions,
                           PricedProtocol[] pricedProtocols,
                           EngineField[][] params) implements Borsh {

  public static final int PORTFOLIO_MANAGER_NAME_LEN = 32;
  public static final int NAME_LEN = 32;
  public static final Discriminator DISCRIMINATOR = toDiscriminator(142, 247, 54, 95, 85, 133, 249, 103);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ACCOUNT_TYPE_OFFSET = 8;
  public static final int ENABLED_OFFSET = 9;
  public static final int VAULT_OFFSET = 10;
  public static final int OWNER_OFFSET = 42;
  public static final int PORTFOLIO_MANAGER_NAME_OFFSET = 74;
  public static final int CREATED_OFFSET = 106;
  public static final int BASE_ASSET_MINT_OFFSET = 154;
  public static final int BASE_ASSET_DECIMALS_OFFSET = 186;
  public static final int BASE_ASSET_TOKEN_PROGRAM_OFFSET = 187;
  public static final int NAME_OFFSET = 188;
  public static final int TIMELOCK_DURATION_OFFSET = 220;
  public static final int TIMELOCK_EXPIRES_AT_OFFSET = 224;
  public static final int MINT_OFFSET = 232;
  public static final int ASSETS_OFFSET = 264;

  public static Filter createAccountTypeFilter(final AccountType accountType) {
    return Filter.createMemCompFilter(ACCOUNT_TYPE_OFFSET, accountType.write());
  }

  public static Filter createEnabledFilter(final boolean enabled) {
    return Filter.createMemCompFilter(ENABLED_OFFSET, new byte[]{(byte) (enabled ? 1 : 0)});
  }

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createCreatedFilter(final CreatedModel created) {
    return Filter.createMemCompFilter(CREATED_OFFSET, created.write());
  }

  public static Filter createBaseAssetMintFilter(final PublicKey baseAssetMint) {
    return Filter.createMemCompFilter(BASE_ASSET_MINT_OFFSET, baseAssetMint);
  }

  public static Filter createBaseAssetDecimalsFilter(final int baseAssetDecimals) {
    return Filter.createMemCompFilter(BASE_ASSET_DECIMALS_OFFSET, new byte[]{(byte) baseAssetDecimals});
  }

  public static Filter createBaseAssetTokenProgramFilter(final int baseAssetTokenProgram) {
    return Filter.createMemCompFilter(BASE_ASSET_TOKEN_PROGRAM_OFFSET, new byte[]{(byte) baseAssetTokenProgram});
  }

  public static Filter createTimelockDurationFilter(final int timelockDuration) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, timelockDuration);
    return Filter.createMemCompFilter(TIMELOCK_DURATION_OFFSET, _data);
  }

  public static Filter createTimelockExpiresAtFilter(final long timelockExpiresAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, timelockExpiresAt);
    return Filter.createMemCompFilter(TIMELOCK_EXPIRES_AT_OFFSET, _data);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static StateAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static StateAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static StateAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], StateAccount> FACTORY = StateAccount::read;

  public static StateAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var accountType = AccountType.read(_data, i);
    i += Borsh.len(accountType);
    final var enabled = _data[i] == 1;
    ++i;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var portfolioManagerName = new byte[32];
    i += Borsh.readArray(portfolioManagerName, _data, i);
    final var created = CreatedModel.read(_data, i);
    i += Borsh.len(created);
    final var baseAssetMint = readPubKey(_data, i);
    i += 32;
    final var baseAssetDecimals = _data[i] & 0xFF;
    ++i;
    final var baseAssetTokenProgram = _data[i] & 0xFF;
    ++i;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var timelockDuration = getInt32LE(_data, i);
    i += 4;
    final var timelockExpiresAt = getInt64LE(_data, i);
    i += 8;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var assets = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(assets);
    final var integrationAcls = Borsh.readVector(IntegrationAcl.class, IntegrationAcl::read, _data, i);
    i += Borsh.lenVector(integrationAcls);
    final var delegateAcls = Borsh.readVector(DelegateAcl.class, DelegateAcl::read, _data, i);
    i += Borsh.lenVector(delegateAcls);
    final var externalPositions = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.lenVector(externalPositions);
    final var pricedProtocols = Borsh.readVector(PricedProtocol.class, PricedProtocol::read, _data, i);
    i += Borsh.lenVector(pricedProtocols);
    final var params = Borsh.readMultiDimensionVector(EngineField.class, EngineField::read, _data, i);
    return new StateAccount(_address,
                            discriminator,
                            accountType,
                            enabled,
                            vault,
                            owner,
                            portfolioManagerName,
                            created,
                            baseAssetMint,
                            baseAssetDecimals,
                            baseAssetTokenProgram,
                            name,
                            timelockDuration,
                            timelockExpiresAt,
                            mint,
                            assets,
                            integrationAcls,
                            delegateAcls,
                            externalPositions,
                            pricedProtocols,
                            params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(accountType, _data, i);
    _data[i] = (byte) (enabled ? 1 : 0);
    ++i;
    vault.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    i += Borsh.writeArray(portfolioManagerName, _data, i);
    i += Borsh.write(created, _data, i);
    baseAssetMint.write(_data, i);
    i += 32;
    _data[i] = (byte) baseAssetDecimals;
    ++i;
    _data[i] = (byte) baseAssetTokenProgram;
    ++i;
    i += Borsh.writeArray(name, _data, i);
    putInt32LE(_data, i, timelockDuration);
    i += 4;
    putInt64LE(_data, i, timelockExpiresAt);
    i += 8;
    mint.write(_data, i);
    i += 32;
    i += Borsh.writeVector(assets, _data, i);
    i += Borsh.writeVector(integrationAcls, _data, i);
    i += Borsh.writeVector(delegateAcls, _data, i);
    i += Borsh.writeVector(externalPositions, _data, i);
    i += Borsh.writeVector(pricedProtocols, _data, i);
    i += Borsh.writeVector(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len(accountType)
         + 1
         + 32
         + 32
         + Borsh.lenArray(portfolioManagerName)
         + Borsh.len(created)
         + 32
         + 1
         + 1
         + Borsh.lenArray(name)
         + 4
         + 8
         + 32
         + Borsh.lenVector(assets)
         + Borsh.lenVector(integrationAcls)
         + Borsh.lenVector(delegateAcls)
         + Borsh.lenVector(externalPositions)
         + Borsh.lenVector(pricedProtocols)
         + Borsh.lenVector(params);
  }
}
