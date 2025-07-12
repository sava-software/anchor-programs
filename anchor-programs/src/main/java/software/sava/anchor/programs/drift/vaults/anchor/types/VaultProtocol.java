package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultProtocol(PublicKey _address,
                            Discriminator discriminator,
                            // The protocol, company, or entity that services the product using this vault.
                            // The protocol is not allowed to deposit into the vault but can profit share and collect annual fees just like the manager.
                            PublicKey protocol,
                            // The shares from profit share and annual fee unclaimed by the protocol.
                            BigInteger protocolProfitAndFeeShares,
                            // The annual fee charged on deposits by the protocol (traditional hedge funds typically charge 2% per year on assets under management).
                            // Unlike the management fee this can't be negative.
                            long protocolFee,
                            // Total withdraws for the protocol
                            long protocolTotalWithdraws,
                            // Total fee charged by the protocol (annual management fee + profit share).
                            // Unlike the management fee this can't be negative.
                            long protocolTotalFee,
                            // Total profit share charged by the protocol
                            long protocolTotalProfitShare,
                            WithdrawRequest lastProtocolWithdrawRequest,
                            // Percentage the protocol charges on all profits realized by depositors: PERCENTAGE_PRECISION
                            int protocolProfitShare,
                            int bump,
                            int version,
                            byte[] padding) implements Borsh {

  public static final int BYTES = 128;
  public static final int PADDING_LEN = 2;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PROTOCOL_OFFSET = 8;
  public static final int PROTOCOL_PROFIT_AND_FEE_SHARES_OFFSET = 40;
  public static final int PROTOCOL_FEE_OFFSET = 56;
  public static final int PROTOCOL_TOTAL_WITHDRAWS_OFFSET = 64;
  public static final int PROTOCOL_TOTAL_FEE_OFFSET = 72;
  public static final int PROTOCOL_TOTAL_PROFIT_SHARE_OFFSET = 80;
  public static final int LAST_PROTOCOL_WITHDRAW_REQUEST_OFFSET = 88;
  public static final int PROTOCOL_PROFIT_SHARE_OFFSET = 120;
  public static final int BUMP_OFFSET = 124;
  public static final int VERSION_OFFSET = 125;
  public static final int PADDING_OFFSET = 126;

  public static Filter createProtocolFilter(final PublicKey protocol) {
    return Filter.createMemCompFilter(PROTOCOL_OFFSET, protocol);
  }

  public static Filter createProtocolProfitAndFeeSharesFilter(final BigInteger protocolProfitAndFeeShares) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, protocolProfitAndFeeShares);
    return Filter.createMemCompFilter(PROTOCOL_PROFIT_AND_FEE_SHARES_OFFSET, _data);
  }

  public static Filter createProtocolFeeFilter(final long protocolFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolFee);
    return Filter.createMemCompFilter(PROTOCOL_FEE_OFFSET, _data);
  }

  public static Filter createProtocolTotalWithdrawsFilter(final long protocolTotalWithdraws) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolTotalWithdraws);
    return Filter.createMemCompFilter(PROTOCOL_TOTAL_WITHDRAWS_OFFSET, _data);
  }

  public static Filter createProtocolTotalFeeFilter(final long protocolTotalFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolTotalFee);
    return Filter.createMemCompFilter(PROTOCOL_TOTAL_FEE_OFFSET, _data);
  }

  public static Filter createProtocolTotalProfitShareFilter(final long protocolTotalProfitShare) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, protocolTotalProfitShare);
    return Filter.createMemCompFilter(PROTOCOL_TOTAL_PROFIT_SHARE_OFFSET, _data);
  }

  public static Filter createLastProtocolWithdrawRequestFilter(final WithdrawRequest lastProtocolWithdrawRequest) {
    return Filter.createMemCompFilter(LAST_PROTOCOL_WITHDRAW_REQUEST_OFFSET, lastProtocolWithdrawRequest.write());
  }

  public static Filter createProtocolProfitShareFilter(final int protocolProfitShare) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, protocolProfitShare);
    return Filter.createMemCompFilter(PROTOCOL_PROFIT_SHARE_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static VaultProtocol read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VaultProtocol read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VaultProtocol read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VaultProtocol> FACTORY = VaultProtocol::read;

  public static VaultProtocol read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var protocol = readPubKey(_data, i);
    i += 32;
    final var protocolProfitAndFeeShares = getInt128LE(_data, i);
    i += 16;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var protocolTotalWithdraws = getInt64LE(_data, i);
    i += 8;
    final var protocolTotalFee = getInt64LE(_data, i);
    i += 8;
    final var protocolTotalProfitShare = getInt64LE(_data, i);
    i += 8;
    final var lastProtocolWithdrawRequest = WithdrawRequest.read(_data, i);
    i += Borsh.len(lastProtocolWithdrawRequest);
    final var protocolProfitShare = getInt32LE(_data, i);
    i += 4;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var version = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[2];
    Borsh.readArray(padding, _data, i);
    return new VaultProtocol(_address,
                             discriminator,
                             protocol,
                             protocolProfitAndFeeShares,
                             protocolFee,
                             protocolTotalWithdraws,
                             protocolTotalFee,
                             protocolTotalProfitShare,
                             lastProtocolWithdrawRequest,
                             protocolProfitShare,
                             bump,
                             version,
                             padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    protocol.write(_data, i);
    i += 32;
    putInt128LE(_data, i, protocolProfitAndFeeShares);
    i += 16;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt64LE(_data, i, protocolTotalWithdraws);
    i += 8;
    putInt64LE(_data, i, protocolTotalFee);
    i += 8;
    putInt64LE(_data, i, protocolTotalProfitShare);
    i += 8;
    i += Borsh.write(lastProtocolWithdrawRequest, _data, i);
    putInt32LE(_data, i, protocolProfitShare);
    i += 4;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) version;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
