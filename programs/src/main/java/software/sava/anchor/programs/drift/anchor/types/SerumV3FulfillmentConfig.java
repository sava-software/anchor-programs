package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SerumV3FulfillmentConfig(PublicKey _address,
                                       byte[] discriminator,
                                       PublicKey pubkey,
                                       PublicKey serumProgramId,
                                       PublicKey serumMarket,
                                       PublicKey serumRequestQueue,
                                       PublicKey serumEventQueue,
                                       PublicKey serumBids,
                                       PublicKey serumAsks,
                                       PublicKey serumBaseVault,
                                       PublicKey serumQuoteVault,
                                       PublicKey serumOpenOrders,
                                       long serumSignerNonce,
                                       int marketIndex,
                                       SpotFulfillmentType fulfillmentType,
                                       SpotFulfillmentConfigStatus status,
                                       int[] padding) implements Borsh {

  public static final int BYTES = 344;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int SERUM_PROGRAM_ID_OFFSET = 40;
  public static final int SERUM_MARKET_OFFSET = 72;
  public static final int SERUM_REQUEST_QUEUE_OFFSET = 104;
  public static final int SERUM_EVENT_QUEUE_OFFSET = 136;
  public static final int SERUM_BIDS_OFFSET = 168;
  public static final int SERUM_ASKS_OFFSET = 200;
  public static final int SERUM_BASE_VAULT_OFFSET = 232;
  public static final int SERUM_QUOTE_VAULT_OFFSET = 264;
  public static final int SERUM_OPEN_ORDERS_OFFSET = 296;
  public static final int SERUM_SIGNER_NONCE_OFFSET = 328;
  public static final int MARKET_INDEX_OFFSET = 336;
  public static final int FULFILLMENT_TYPE_OFFSET = 338;
  public static final int STATUS_OFFSET = 339;
  public static final int PADDING_OFFSET = 340;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createSerumProgramIdFilter(final PublicKey serumProgramId) {
    return Filter.createMemCompFilter(SERUM_PROGRAM_ID_OFFSET, serumProgramId);
  }

  public static Filter createSerumMarketFilter(final PublicKey serumMarket) {
    return Filter.createMemCompFilter(SERUM_MARKET_OFFSET, serumMarket);
  }

  public static Filter createSerumRequestQueueFilter(final PublicKey serumRequestQueue) {
    return Filter.createMemCompFilter(SERUM_REQUEST_QUEUE_OFFSET, serumRequestQueue);
  }

  public static Filter createSerumEventQueueFilter(final PublicKey serumEventQueue) {
    return Filter.createMemCompFilter(SERUM_EVENT_QUEUE_OFFSET, serumEventQueue);
  }

  public static Filter createSerumBidsFilter(final PublicKey serumBids) {
    return Filter.createMemCompFilter(SERUM_BIDS_OFFSET, serumBids);
  }

  public static Filter createSerumAsksFilter(final PublicKey serumAsks) {
    return Filter.createMemCompFilter(SERUM_ASKS_OFFSET, serumAsks);
  }

  public static Filter createSerumBaseVaultFilter(final PublicKey serumBaseVault) {
    return Filter.createMemCompFilter(SERUM_BASE_VAULT_OFFSET, serumBaseVault);
  }

  public static Filter createSerumQuoteVaultFilter(final PublicKey serumQuoteVault) {
    return Filter.createMemCompFilter(SERUM_QUOTE_VAULT_OFFSET, serumQuoteVault);
  }

  public static Filter createSerumOpenOrdersFilter(final PublicKey serumOpenOrders) {
    return Filter.createMemCompFilter(SERUM_OPEN_ORDERS_OFFSET, serumOpenOrders);
  }

  public static Filter createSerumSignerNonceFilter(final long serumSignerNonce) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, serumSignerNonce);
    return Filter.createMemCompFilter(SERUM_SIGNER_NONCE_OFFSET, _data);
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

  public static SerumV3FulfillmentConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static SerumV3FulfillmentConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], SerumV3FulfillmentConfig> FACTORY = SerumV3FulfillmentConfig::read;

  public static SerumV3FulfillmentConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    final byte[] discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var serumProgramId = readPubKey(_data, i);
    i += 32;
    final var serumMarket = readPubKey(_data, i);
    i += 32;
    final var serumRequestQueue = readPubKey(_data, i);
    i += 32;
    final var serumEventQueue = readPubKey(_data, i);
    i += 32;
    final var serumBids = readPubKey(_data, i);
    i += 32;
    final var serumAsks = readPubKey(_data, i);
    i += 32;
    final var serumBaseVault = readPubKey(_data, i);
    i += 32;
    final var serumQuoteVault = readPubKey(_data, i);
    i += 32;
    final var serumOpenOrders = readPubKey(_data, i);
    i += 32;
    final var serumSignerNonce = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var fulfillmentType = SpotFulfillmentType.read(_data, i);
    i += Borsh.len(fulfillmentType);
    final var status = SpotFulfillmentConfigStatus.read(_data, i);
    i += Borsh.len(status);
    final var padding = Borsh.readArray(new int[4], _data, i);
    return new SerumV3FulfillmentConfig(_address,
                                        discriminator,
                                        pubkey,
                                        serumProgramId,
                                        serumMarket,
                                        serumRequestQueue,
                                        serumEventQueue,
                                        serumBids,
                                        serumAsks,
                                        serumBaseVault,
                                        serumQuoteVault,
                                        serumOpenOrders,
                                        serumSignerNonce,
                                        marketIndex,
                                        fulfillmentType,
                                        status,
                                        padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    System.arraycopy(discriminator, 0, _data, offset, discriminator.length);
    int i = offset + discriminator.length;
    pubkey.write(_data, i);
    i += 32;
    serumProgramId.write(_data, i);
    i += 32;
    serumMarket.write(_data, i);
    i += 32;
    serumRequestQueue.write(_data, i);
    i += 32;
    serumEventQueue.write(_data, i);
    i += 32;
    serumBids.write(_data, i);
    i += 32;
    serumAsks.write(_data, i);
    i += 32;
    serumBaseVault.write(_data, i);
    i += 32;
    serumQuoteVault.write(_data, i);
    i += 32;
    serumOpenOrders.write(_data, i);
    i += 32;
    putInt64LE(_data, i, serumSignerNonce);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(fulfillmentType, _data, i);
    i += Borsh.write(status, _data, i);
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 32
         + 8
         + 2
         + Borsh.len(fulfillmentType)
         + Borsh.len(status)
         + Borsh.fixedLen(padding);
  }
}
