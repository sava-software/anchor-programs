package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record ClusterHistory(PublicKey _address,
                             Discriminator discriminator,
                             long structVersion,
                             int bump,
                             byte[] padding0,
                             long clusterHistoryLastUpdateSlot,
                             byte[] padding1,
                             CircBufCluster history) implements Borsh {

  public static final int BYTES = 131352;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(41, 154, 241, 80, 135, 88, 85, 252);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int STRUCT_VERSION_OFFSET = 8;
  public static final int BUMP_OFFSET = 16;
  public static final int PADDING0_OFFSET = 17;
  public static final int CLUSTER_HISTORY_LAST_UPDATE_SLOT_OFFSET = 24;
  public static final int PADDING1_OFFSET = 32;
  public static final int HISTORY_OFFSET = 264;

  public static Filter createStructVersionFilter(final long structVersion) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, structVersion);
    return Filter.createMemCompFilter(STRUCT_VERSION_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createClusterHistoryLastUpdateSlotFilter(final long clusterHistoryLastUpdateSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, clusterHistoryLastUpdateSlot);
    return Filter.createMemCompFilter(CLUSTER_HISTORY_LAST_UPDATE_SLOT_OFFSET, _data);
  }

  public static ClusterHistory read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ClusterHistory read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ClusterHistory> FACTORY = ClusterHistory::read;

  public static ClusterHistory read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var structVersion = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[7];
    i += Borsh.readArray(padding0, _data, i);
    final var clusterHistoryLastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new byte[232];
    i += Borsh.readArray(padding1, _data, i);
    final var history = CircBufCluster.read(_data, i);
    return new ClusterHistory(_address,
                              discriminator,
                              structVersion,
                              bump,
                              padding0,
                              clusterHistoryLastUpdateSlot,
                              padding1,
                              history);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, structVersion);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    putInt64LE(_data, i, clusterHistoryLastUpdateSlot);
    i += 8;
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.write(history, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
