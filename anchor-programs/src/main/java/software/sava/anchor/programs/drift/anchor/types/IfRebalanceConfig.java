package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record IfRebalanceConfig(PublicKey _address,
                                Discriminator discriminator,
                                PublicKey pubkey,
                                // total amount to be sold
                                long totalInAmount,
                                // amount already sold
                                long currentInAmount,
                                // amount already bought
                                long currentOutAmount,
                                // amount already transferred to revenue pool
                                long currentOutAmountTransferred,
                                // amount already bought in epoch
                                long currentInAmountSinceLastTransfer,
                                // start time of epoch
                                long epochStartTs,
                                // amount already bought in epoch
                                long epochInAmount,
                                // max amount to swap in epoch
                                long epochMaxInAmount,
                                // duration of epoch
                                long epochDuration,
                                // market index to sell
                                int outMarketIndex,
                                // market index to buy
                                int inMarketIndex,
                                int maxSlippageBps,
                                int swapMode,
                                int status,
                                byte[] padding2) implements Borsh {

  public static final int BYTES = 152;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int TOTAL_IN_AMOUNT_OFFSET = 40;
  public static final int CURRENT_IN_AMOUNT_OFFSET = 48;
  public static final int CURRENT_OUT_AMOUNT_OFFSET = 56;
  public static final int CURRENT_OUT_AMOUNT_TRANSFERRED_OFFSET = 64;
  public static final int CURRENT_IN_AMOUNT_SINCE_LAST_TRANSFER_OFFSET = 72;
  public static final int EPOCH_START_TS_OFFSET = 80;
  public static final int EPOCH_IN_AMOUNT_OFFSET = 88;
  public static final int EPOCH_MAX_IN_AMOUNT_OFFSET = 96;
  public static final int EPOCH_DURATION_OFFSET = 104;
  public static final int OUT_MARKET_INDEX_OFFSET = 112;
  public static final int IN_MARKET_INDEX_OFFSET = 114;
  public static final int MAX_SLIPPAGE_BPS_OFFSET = 116;
  public static final int SWAP_MODE_OFFSET = 118;
  public static final int STATUS_OFFSET = 119;
  public static final int PADDING2_OFFSET = 120;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createTotalInAmountFilter(final long totalInAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalInAmount);
    return Filter.createMemCompFilter(TOTAL_IN_AMOUNT_OFFSET, _data);
  }

  public static Filter createCurrentInAmountFilter(final long currentInAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, currentInAmount);
    return Filter.createMemCompFilter(CURRENT_IN_AMOUNT_OFFSET, _data);
  }

  public static Filter createCurrentOutAmountFilter(final long currentOutAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, currentOutAmount);
    return Filter.createMemCompFilter(CURRENT_OUT_AMOUNT_OFFSET, _data);
  }

  public static Filter createCurrentOutAmountTransferredFilter(final long currentOutAmountTransferred) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, currentOutAmountTransferred);
    return Filter.createMemCompFilter(CURRENT_OUT_AMOUNT_TRANSFERRED_OFFSET, _data);
  }

  public static Filter createCurrentInAmountSinceLastTransferFilter(final long currentInAmountSinceLastTransfer) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, currentInAmountSinceLastTransfer);
    return Filter.createMemCompFilter(CURRENT_IN_AMOUNT_SINCE_LAST_TRANSFER_OFFSET, _data);
  }

  public static Filter createEpochStartTsFilter(final long epochStartTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochStartTs);
    return Filter.createMemCompFilter(EPOCH_START_TS_OFFSET, _data);
  }

  public static Filter createEpochInAmountFilter(final long epochInAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochInAmount);
    return Filter.createMemCompFilter(EPOCH_IN_AMOUNT_OFFSET, _data);
  }

  public static Filter createEpochMaxInAmountFilter(final long epochMaxInAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochMaxInAmount);
    return Filter.createMemCompFilter(EPOCH_MAX_IN_AMOUNT_OFFSET, _data);
  }

  public static Filter createEpochDurationFilter(final long epochDuration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epochDuration);
    return Filter.createMemCompFilter(EPOCH_DURATION_OFFSET, _data);
  }

  public static Filter createOutMarketIndexFilter(final int outMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, outMarketIndex);
    return Filter.createMemCompFilter(OUT_MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createInMarketIndexFilter(final int inMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, inMarketIndex);
    return Filter.createMemCompFilter(IN_MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createMaxSlippageBpsFilter(final int maxSlippageBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, maxSlippageBps);
    return Filter.createMemCompFilter(MAX_SLIPPAGE_BPS_OFFSET, _data);
  }

  public static Filter createSwapModeFilter(final int swapMode) {
    return Filter.createMemCompFilter(SWAP_MODE_OFFSET, new byte[]{(byte) swapMode});
  }

  public static Filter createStatusFilter(final int status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, new byte[]{(byte) status});
  }

  public static IfRebalanceConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static IfRebalanceConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static IfRebalanceConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], IfRebalanceConfig> FACTORY = IfRebalanceConfig::read;

  public static IfRebalanceConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var totalInAmount = getInt64LE(_data, i);
    i += 8;
    final var currentInAmount = getInt64LE(_data, i);
    i += 8;
    final var currentOutAmount = getInt64LE(_data, i);
    i += 8;
    final var currentOutAmountTransferred = getInt64LE(_data, i);
    i += 8;
    final var currentInAmountSinceLastTransfer = getInt64LE(_data, i);
    i += 8;
    final var epochStartTs = getInt64LE(_data, i);
    i += 8;
    final var epochInAmount = getInt64LE(_data, i);
    i += 8;
    final var epochMaxInAmount = getInt64LE(_data, i);
    i += 8;
    final var epochDuration = getInt64LE(_data, i);
    i += 8;
    final var outMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var inMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var maxSlippageBps = getInt16LE(_data, i);
    i += 2;
    final var swapMode = _data[i] & 0xFF;
    ++i;
    final var status = _data[i] & 0xFF;
    ++i;
    final var padding2 = new byte[32];
    Borsh.readArray(padding2, _data, i);
    return new IfRebalanceConfig(_address,
                                 discriminator,
                                 pubkey,
                                 totalInAmount,
                                 currentInAmount,
                                 currentOutAmount,
                                 currentOutAmountTransferred,
                                 currentInAmountSinceLastTransfer,
                                 epochStartTs,
                                 epochInAmount,
                                 epochMaxInAmount,
                                 epochDuration,
                                 outMarketIndex,
                                 inMarketIndex,
                                 maxSlippageBps,
                                 swapMode,
                                 status,
                                 padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, totalInAmount);
    i += 8;
    putInt64LE(_data, i, currentInAmount);
    i += 8;
    putInt64LE(_data, i, currentOutAmount);
    i += 8;
    putInt64LE(_data, i, currentOutAmountTransferred);
    i += 8;
    putInt64LE(_data, i, currentInAmountSinceLastTransfer);
    i += 8;
    putInt64LE(_data, i, epochStartTs);
    i += 8;
    putInt64LE(_data, i, epochInAmount);
    i += 8;
    putInt64LE(_data, i, epochMaxInAmount);
    i += 8;
    putInt64LE(_data, i, epochDuration);
    i += 8;
    putInt16LE(_data, i, outMarketIndex);
    i += 2;
    putInt16LE(_data, i, inMarketIndex);
    i += 2;
    putInt16LE(_data, i, maxSlippageBps);
    i += 2;
    _data[i] = (byte) swapMode;
    ++i;
    _data[i] = (byte) status;
    ++i;
    i += Borsh.writeArray(padding2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
