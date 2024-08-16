package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record PhoenixV1FulfillmentConfig(PublicKey _address,
                                         Discriminator discriminator,
                                         PublicKey pubkey,
                                         PublicKey phoenixProgramId,
                                         PublicKey phoenixLogAuthority,
                                         PublicKey phoenixMarket,
                                         PublicKey phoenixBaseVault,
                                         PublicKey phoenixQuoteVault,
                                         int marketIndex,
                                         SpotFulfillmentType fulfillmentType,
                                         SpotFulfillmentConfigStatus status,
                                         int[] padding) implements Borsh {

  public static final int BYTES = 208;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int PHOENIX_PROGRAM_ID_OFFSET = 40;
  public static final int PHOENIX_LOG_AUTHORITY_OFFSET = 72;
  public static final int PHOENIX_MARKET_OFFSET = 104;
  public static final int PHOENIX_BASE_VAULT_OFFSET = 136;
  public static final int PHOENIX_QUOTE_VAULT_OFFSET = 168;
  public static final int MARKET_INDEX_OFFSET = 200;
  public static final int FULFILLMENT_TYPE_OFFSET = 202;
  public static final int STATUS_OFFSET = 203;
  public static final int PADDING_OFFSET = 204;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createPhoenixProgramIdFilter(final PublicKey phoenixProgramId) {
    return Filter.createMemCompFilter(PHOENIX_PROGRAM_ID_OFFSET, phoenixProgramId);
  }

  public static Filter createPhoenixLogAuthorityFilter(final PublicKey phoenixLogAuthority) {
    return Filter.createMemCompFilter(PHOENIX_LOG_AUTHORITY_OFFSET, phoenixLogAuthority);
  }

  public static Filter createPhoenixMarketFilter(final PublicKey phoenixMarket) {
    return Filter.createMemCompFilter(PHOENIX_MARKET_OFFSET, phoenixMarket);
  }

  public static Filter createPhoenixBaseVaultFilter(final PublicKey phoenixBaseVault) {
    return Filter.createMemCompFilter(PHOENIX_BASE_VAULT_OFFSET, phoenixBaseVault);
  }

  public static Filter createPhoenixQuoteVaultFilter(final PublicKey phoenixQuoteVault) {
    return Filter.createMemCompFilter(PHOENIX_QUOTE_VAULT_OFFSET, phoenixQuoteVault);
  }

  public static Filter createMarketIndexFilter(final int marketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, marketIndex);
    return Filter.createMemCompFilter(MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createFulfillmentTypeFilter(final SpotFulfillmentType fulfillmentType) {
    return Filter.createMemCompFilter(FULFILLMENT_TYPE_OFFSET, fulfillmentType.write());
  }

  public static Filter createStatusFilter(final SpotFulfillmentConfigStatus status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, status.write());
  }

  public static PhoenixV1FulfillmentConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PhoenixV1FulfillmentConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PhoenixV1FulfillmentConfig> FACTORY = PhoenixV1FulfillmentConfig::read;

  public static PhoenixV1FulfillmentConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var phoenixProgramId = readPubKey(_data, i);
    i += 32;
    final var phoenixLogAuthority = readPubKey(_data, i);
    i += 32;
    final var phoenixMarket = readPubKey(_data, i);
    i += 32;
    final var phoenixBaseVault = readPubKey(_data, i);
    i += 32;
    final var phoenixQuoteVault = readPubKey(_data, i);
    i += 32;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var fulfillmentType = SpotFulfillmentType.read(_data, i);
    i += Borsh.len(fulfillmentType);
    final var status = SpotFulfillmentConfigStatus.read(_data, i);
    i += Borsh.len(status);
    final var padding = Borsh.readArray(new int[4], _data, i);
    return new PhoenixV1FulfillmentConfig(_address,
                                          discriminator,
                                          pubkey,
                                          phoenixProgramId,
                                          phoenixLogAuthority,
                                          phoenixMarket,
                                          phoenixBaseVault,
                                          phoenixQuoteVault,
                                          marketIndex,
                                          fulfillmentType,
                                          status,
                                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    phoenixProgramId.write(_data, i);
    i += 32;
    phoenixLogAuthority.write(_data, i);
    i += 32;
    phoenixMarket.write(_data, i);
    i += 32;
    phoenixBaseVault.write(_data, i);
    i += 32;
    phoenixQuoteVault.write(_data, i);
    i += 32;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(fulfillmentType, _data, i);
    i += Borsh.write(status, _data, i);
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
