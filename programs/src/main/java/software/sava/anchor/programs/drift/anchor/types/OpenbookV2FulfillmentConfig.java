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

public record OpenbookV2FulfillmentConfig(PublicKey _address,
                                          Discriminator discriminator,
                                          PublicKey pubkey,
                                          PublicKey openbookV2ProgramId,
                                          PublicKey openbookV2Market,
                                          PublicKey openbookV2MarketAuthority,
                                          PublicKey openbookV2EventHeap,
                                          PublicKey openbookV2Bids,
                                          PublicKey openbookV2Asks,
                                          PublicKey openbookV2BaseVault,
                                          PublicKey openbookV2QuoteVault,
                                          int marketIndex,
                                          SpotFulfillmentType fulfillmentType,
                                          SpotFulfillmentConfigStatus status,
                                          int[] padding) implements Borsh {

  public static final int BYTES = 304;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int OPENBOOK_V2_PROGRAM_ID_OFFSET = 40;
  public static final int OPENBOOK_V2_MARKET_OFFSET = 72;
  public static final int OPENBOOK_V2_MARKET_AUTHORITY_OFFSET = 104;
  public static final int OPENBOOK_V2_EVENT_HEAP_OFFSET = 136;
  public static final int OPENBOOK_V2_BIDS_OFFSET = 168;
  public static final int OPENBOOK_V2_ASKS_OFFSET = 200;
  public static final int OPENBOOK_V2_BASE_VAULT_OFFSET = 232;
  public static final int OPENBOOK_V2_QUOTE_VAULT_OFFSET = 264;
  public static final int MARKET_INDEX_OFFSET = 296;
  public static final int FULFILLMENT_TYPE_OFFSET = 298;
  public static final int STATUS_OFFSET = 299;
  public static final int PADDING_OFFSET = 300;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createOpenbookV2ProgramIdFilter(final PublicKey openbookV2ProgramId) {
    return Filter.createMemCompFilter(OPENBOOK_V2_PROGRAM_ID_OFFSET, openbookV2ProgramId);
  }

  public static Filter createOpenbookV2MarketFilter(final PublicKey openbookV2Market) {
    return Filter.createMemCompFilter(OPENBOOK_V2_MARKET_OFFSET, openbookV2Market);
  }

  public static Filter createOpenbookV2MarketAuthorityFilter(final PublicKey openbookV2MarketAuthority) {
    return Filter.createMemCompFilter(OPENBOOK_V2_MARKET_AUTHORITY_OFFSET, openbookV2MarketAuthority);
  }

  public static Filter createOpenbookV2EventHeapFilter(final PublicKey openbookV2EventHeap) {
    return Filter.createMemCompFilter(OPENBOOK_V2_EVENT_HEAP_OFFSET, openbookV2EventHeap);
  }

  public static Filter createOpenbookV2BidsFilter(final PublicKey openbookV2Bids) {
    return Filter.createMemCompFilter(OPENBOOK_V2_BIDS_OFFSET, openbookV2Bids);
  }

  public static Filter createOpenbookV2AsksFilter(final PublicKey openbookV2Asks) {
    return Filter.createMemCompFilter(OPENBOOK_V2_ASKS_OFFSET, openbookV2Asks);
  }

  public static Filter createOpenbookV2BaseVaultFilter(final PublicKey openbookV2BaseVault) {
    return Filter.createMemCompFilter(OPENBOOK_V2_BASE_VAULT_OFFSET, openbookV2BaseVault);
  }

  public static Filter createOpenbookV2QuoteVaultFilter(final PublicKey openbookV2QuoteVault) {
    return Filter.createMemCompFilter(OPENBOOK_V2_QUOTE_VAULT_OFFSET, openbookV2QuoteVault);
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

  public static OpenbookV2FulfillmentConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static OpenbookV2FulfillmentConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], OpenbookV2FulfillmentConfig> FACTORY = OpenbookV2FulfillmentConfig::read;

  public static OpenbookV2FulfillmentConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var openbookV2ProgramId = readPubKey(_data, i);
    i += 32;
    final var openbookV2Market = readPubKey(_data, i);
    i += 32;
    final var openbookV2MarketAuthority = readPubKey(_data, i);
    i += 32;
    final var openbookV2EventHeap = readPubKey(_data, i);
    i += 32;
    final var openbookV2Bids = readPubKey(_data, i);
    i += 32;
    final var openbookV2Asks = readPubKey(_data, i);
    i += 32;
    final var openbookV2BaseVault = readPubKey(_data, i);
    i += 32;
    final var openbookV2QuoteVault = readPubKey(_data, i);
    i += 32;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var fulfillmentType = SpotFulfillmentType.read(_data, i);
    i += Borsh.len(fulfillmentType);
    final var status = SpotFulfillmentConfigStatus.read(_data, i);
    i += Borsh.len(status);
    final var padding = Borsh.readArray(new int[4], _data, i);
    return new OpenbookV2FulfillmentConfig(_address,
                                           discriminator,
                                           pubkey,
                                           openbookV2ProgramId,
                                           openbookV2Market,
                                           openbookV2MarketAuthority,
                                           openbookV2EventHeap,
                                           openbookV2Bids,
                                           openbookV2Asks,
                                           openbookV2BaseVault,
                                           openbookV2QuoteVault,
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
    openbookV2ProgramId.write(_data, i);
    i += 32;
    openbookV2Market.write(_data, i);
    i += 32;
    openbookV2MarketAuthority.write(_data, i);
    i += 32;
    openbookV2EventHeap.write(_data, i);
    i += 32;
    openbookV2Bids.write(_data, i);
    i += 32;
    openbookV2Asks.write(_data, i);
    i += 32;
    openbookV2BaseVault.write(_data, i);
    i += 32;
    openbookV2QuoteVault.write(_data, i);
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
